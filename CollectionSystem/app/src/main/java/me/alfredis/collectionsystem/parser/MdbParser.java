package me.alfredis.collectionsystem.parser;


import com.healthmarketscience.jackcess.*;
import com.healthmarketscience.jackcess.util.ImportUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
/**
 * Created by jishshi on 2015/5/10.
 */
public class MdbParser {

        /**
         * @param args
         * @throws IOException
         */
        public static void main(String[] args) throws IOException {
            // getAccessDataTable();
            //createTable();
            //createTableRecByCsv() ;
            deleteRec();
        }
        /**
         * 读取指定表格
         * @throws IOException
         */
        public static void getAccessDataTable() throws IOException{
            Table table = DatabaseBuilder.open(new File("c:\\data.mdb")).getTable("AspCms_Collect");
            for(Row row : table) {
                System.out.println("--ID--" + row.get("CollectID")+"--Name--" + row.get("CollectName"));
            }
        }
        /**
         * 创建表并写入数据
         * @throws IOException
         */
        public static void createTable() throws IOException{
            Database db = DatabaseBuilder.create(Database.FileFormat.V2000, new File("c:\\new.mdb"));
            Table newTable;
            try {
                newTable = new TableBuilder("NewTable")
                        .addColumn(new ColumnBuilder("a")
                                .setSQLType(Types.INTEGER))
                        .addColumn(new ColumnBuilder("b")
                                .setSQLType(Types.VARCHAR))
                        .toTable(db);
                newTable.addRow(1, "foo");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        /**
         *  将CSV文件的内容复制到一个新表：
         * @throws IOException
         */
        public  static void createTableRecByCsv() throws IOException{
            Database db = DatabaseBuilder.open(new File("c:\\new.mdb"));
            new ImportUtil.Builder(db, "NewTable22").setDelimiter(",").importFile(new File("c:\\test.csv"));
            db.close();
        }
        /**
         * 删除一条数据
         * @throws IOException
         */
        public static void deleteRec() throws IOException{
            Table table = DatabaseBuilder.open(new File("c:\\new.mdb")).getTable("NewTable22");
            Row row = CursorBuilder.findRow(table, Collections.singletonMap("xh", "4"));
            if(row != null) {
                System.out.println("Found row where 'a' == 'foo': " + row);
                table.deleteRow(row);
            } else {
                System.out.println("Could not find row where 'a' == 'foo'");
            }
        }


    }

