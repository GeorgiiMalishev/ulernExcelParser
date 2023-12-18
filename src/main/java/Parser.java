import entities.*;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.util.*;
import java.io.FileInputStream;
import java.util.regex.Pattern;


public class  Parser{
    public static void Parse(String excelFilePath) {

        try (FileInputStream inputStream = new FileInputStream(excelFilePath)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            var students = parseStudents(getCoursePattern(sheet), sheet);
            //StudentUtil.insertStudents(students);
            new Chart("Зависимость среднего балла от имени").showBarChart("Имя студента", "Средние баллы", StudentUtil.getNameScoreMap(students));
            new Chart("Количество студентов с одним именем").showBarChart( "Имя студента", "Количество студентов", StudentUtil.getNameCountMap(students));
            new Chart("Топ 10 студентов по среднему баллу").showBarChart("Имя студента", "Средние баллы", StudentUtil.getTopStudentsMap(students, 10));
            new Chart("").showPieChart(StudentUtil.getNameScoreMap(students));
            new Chart("").showPieChart(StudentUtil.getNameCountMap(students));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Course getCoursePattern(Sheet sheet){
        var taskDict = new HashMap<String, TaskType>();
        taskDict.put("Упр", TaskType.Exercise);
        taskDict.put("ДЗ",  TaskType.Practise);
        taskDict.put("Доп",  TaskType.Practise);
        taskDict.put("Акт",  TaskType.Activity);
        taskDict.put("Сем",  TaskType.Seminar);
        var coursePattern = new Course();
        var chapterIndex = -1;
        for(int i = 10; sheet.getRow(1).getCell(i) != null; i++) {
            var secondRow = sheet.getRow(1).getCell(i).toString();
            if (secondRow.equals("Акт")){
                coursePattern.addChapter(new Chapter(sheet.getRow(0).getCell(i).toString()));
                chapterIndex++;
            }
            var parts = secondRow.split(": ");
            if (parts.length >= 2) coursePattern.getChapter(chapterIndex).addTask(new Task(parts[1], taskDict.get(parts[0]), i));
            else coursePattern.getChapter(chapterIndex).addTask(new Task(parts[0], taskDict.get(parts[0]), i));
        }

        return coursePattern;
    }
    private static List<Student> parseStudents(Course coursePattern, Sheet sheet){
        var students = new ArrayList<Student>();
        for (int i = 3; sheet.getRow(i) != null; i++){
            var name = sheet.getRow(i).getCell(0).toString();
            if (!isValidName(name)) continue;
            var id = sheet.getRow(i).getCell(1).toString();
            var email = sheet.getRow(i).getCell(2).toString();
            var group = sheet.getRow(i).getCell(3).toString();
            var nameParts = name.split("\\s+", 2);
            var student = new Student(nameParts[1], nameParts[0], email, group, id);
            var course = parseCourse(coursePattern, sheet, id, i);

            student.addCourse(course);
            students.add(student);
        }

        return students;
    }

    private static Course parseCourse(Course coursePattern, Sheet sheet, String studentId, int index){
        var activityScore = (int)sheet.getRow(index).getCell(4).getNumericCellValue();
        var exerciseScore = (int)sheet.getRow(index).getCell(5).getNumericCellValue();
        var practiseScore = (int)sheet.getRow(index).getCell(6).getNumericCellValue();
        var seminarScore = (int)sheet.getRow(index).getCell(7).getNumericCellValue();
        var courseId = UUID.randomUUID();
        var course = new Course(courseId, studentId, exerciseScore, practiseScore, seminarScore, activityScore);

        addChapters(coursePattern, course, sheet, courseId, index);
        return course;
    }

    private static void addChapters(Course coursePattern, Course course, Sheet sheet, UUID courseId, int index){
        for (int i = 0; i < coursePattern.getChaptersSize(); i++){
            var chapterId = UUID.randomUUID();
            var chapter = new Chapter(coursePattern.getChapter(i), chapterId, courseId);
            var secondRow = sheet.getRow(1).getCell(i).toString();
            for (var task : coursePattern.getChapter(i).tasks) {
                var score = (int)sheet.getRow(index).getCell(task.getColumnIndex()).getNumericCellValue();
                if ("Упр Сем ДЗ Акт".contains(task.getName())){
                    chapter.addScore(task, score);
                    continue;
                }
                chapter.addTask(new Task(task, UUID.randomUUID(), chapterId, score));
            }
            course.addChapter(chapter);
        }
    }

    private static boolean isValidName(String name){
        return !Pattern.compile("[a-zA-Z]").matcher(name).matches() && !(name.split(" ").length < 2);
    }
}