import entities.*;
import org.apache.poi.ss.usermodel.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class  Parser{
    public static void main(String[] args) {
        var taskDict = new HashMap<String, TaskType>();
        taskDict.put("Упр", TaskType.Exercise);
        taskDict.put("ДЗ",  TaskType.Practise);
        taskDict.put("Доп",  TaskType.Practise);



        String excelFilePath = "basicprogramming.xlsx";
        Course coursePattern;
        Connection connection = SQLiteExample.getConnection();
        try (FileInputStream inputStream = new FileInputStream(excelFilePath)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Получение первого листа

            System.out.println(sheet.getRow(0).getCell(10));
            coursePattern = new Course();
            var chapterIndex = -1;
            for(int i = 10; sheet.getRow(1).getCell(i) != null; i++) {
                var secondRow = sheet.getRow(1).getCell(i).toString();
                if (secondRow.equals("Акт")){
                    coursePattern.addChapter(new Chapter(sheet.getRow(0).getCell(i).toString()));
                    chapterIndex++;
                } else if(!"Упр, Сем, ДЗ".contains(secondRow)){
                    var parts = secondRow.split(": ");

                    if (parts.length == 2) coursePattern.getChapter(chapterIndex).addTask(new Task(parts[1], taskDict.get(parts[0]), i));
                    else coursePattern.getChapter(chapterIndex).addTask(new Task("", taskDict.get(parts[0]), i));
                }
            }

            var students = new ArrayList<Student>();
            for (int i = 3; sheet.getRow(i) != null; i++){
                var name = sheet.getRow(i).getCell(0).toString();
                if (!isValidName(name)) continue;
                var id = sheet.getRow(i).getCell(1).toString();
                var email = sheet.getRow(i).getCell(2).toString();
                var group = sheet.getRow(i).getCell(3).toString();
                var activityScore = (int)sheet.getRow(i).getCell(4).getNumericCellValue();
                var exerciseScore = (int)sheet.getRow(i).getCell(5).getNumericCellValue();
                var practiseScore = (int)sheet.getRow(i).getCell(6).getNumericCellValue();
                var seminarScore = (int)sheet.getRow(i).getCell(7).getNumericCellValue();
                var nameParts = name.split("\\s+", 2);
                var student = new Student(nameParts[0], nameParts[1], email, group, id);
                var courseId = UUID.randomUUID();
                var course = new Course(courseId, id, exerciseScore, practiseScore, seminarScore, activityScore);
                for (int j = 0; j < coursePattern.getChaptersSize(); j++){
                    var chapterId = UUID.randomUUID();
                    var chapter = new Chapter(coursePattern.getChapter(j), chapterId, courseId);
                    for (var task : coursePattern.getChapter(j).tasks) {
                        chapter.addTask(new Task(task, UUID.randomUUID(), chapterId, (int)sheet.getRow(i).getCell(task.getColumnIndex()).getNumericCellValue()));
                    }
                    course.addChapter(chapter);
                }
                student.addCourse(course);
                connection.setAutoCommit(false);
                student.insertData(connection);
                students.add(student);
                connection.commit();
                System.out.println(i);
            }
            
            workbook.close();
        } catch (Exception e) {
            try {
                connection.rollback();
            }
            catch (Exception e2){
                e2.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            try{
                connection.setAutoCommit(true);
            } catch (Exception e){
            e.printStackTrace();
        }
        }
    }

    private static boolean isValidName(String name){
        return !Pattern.compile("[a-zA-Z]").matcher(name).matches() && !(name.split(" ").length < 2);
    }
}