import entities.Student;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class StudentUtil {
    public static Map<String, Double> getNameScoreMap(List<Student> students) {
        Map<String, Double> nameScoreMap = new HashMap<>();
        var nameCountMap = getNameCountMap(students);
        var nameSumScoreMap = getNameSumScoreMap(students);
        for(var name : nameCountMap.keySet()){
            nameScoreMap.put(name, nameSumScoreMap.get(name)/nameCountMap.get(name));
        }

        Map<String, Double> sortedMap = nameScoreMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue(),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        return sortedMap;
    }

    public static Map<String, Double> getNameCountMap(List<Student> students){
        Map<String, Double> nameCountMap = new HashMap<>();
        for (Student student : students) {
            var name = student.getFirstname();
            nameCountMap.put(name, nameCountMap.getOrDefault(name, 0.0) + 1);
        }

        return nameCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 2)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public static Map<String, Double> getNameSumScoreMap(List<Student> students){
        Map<String, Double> nameSumScoreMap = new HashMap<>();
        for (Student student : students) {
            var score = student.getAverageScore();
            var name = student.getFirstname();
            nameSumScoreMap.put(name, nameSumScoreMap.getOrDefault(name, 0.0) + score);
        }

        return nameSumScoreMap;
    }

    public static Map<String, Double> getTopStudentsMap(List<Student> students, int n){
        Map<String, Double> topStudents = new HashMap<>();
        for (Student student : students) {
            var score = student.getAverageScore();
            var name = student.getFirstname() + " " + student.getLastname();
            topStudents.put(name,  Double.max(score, topStudents.getOrDefault(name, 0.0)));
        }

        return topStudents.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public Map<String, Double> getScorePractiseMap(List<Student> students){
        Map<String, Double> scores = new LinkedHashMap<>();

    }

    public static void insertStudents(List<Student> students){
        try (var connection = SQLiteConnector.getConnection()) {
            connection.setAutoCommit(false);
            students.forEach(s -> {
                try {
                    s.insertData(connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            connection.commit();
            System.out.println("Загрузка в БД завершена");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}