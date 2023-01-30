package test;

import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.model.ExternalDependency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradleDependencyParser {

    public static void main(String[] args) {
        parseDependencies("C:\\Users\\danie\\IdeaProjects\\Test\\target\\Project\\build.gradlek");
    }


    public static List<Dependency> parseDependencies(String filePath) {
        List<Dependency> dependencies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String regex1 = "^\\s*(compile|implementation|api|testCompile|testImplementation|compileOnly|runtimeOnly)\\s+\\'(.*):(.*):(.*)?\\'";
                String regex2 = "^\\s*(compile|implementation|api|testCompile|testImplementation|compileOnly|runtimeOnly)\\s+\\'(.*):(.*)'$";
                String regex3 = "^\\s*(compile|implementation|api|testCompile|testImplementation|compileOnly|runtimeOnly)\\(+\"(.*):(.*):(.*)?\"\\)";
                String regex4 = "^\\s*(compile|implementation|api|testCompile|testImplementation|compileOnly|runtimeOnly)\\(+\"(.*):(.*)\"\\)$";
                String regex5 = "^\\s*(compile|implementation|api|testCompile|testImplementation|compileOnly|runtimeOnly)\\s+\\s*group:\\s+\\'(.*)\\',\\s+name:\\s+\\'(.*)\\',\\s+version:\\s+\\'(.*)?\\'";
                String regex6 = "^\\s*(compile|implementation|api|testCompile|testImplementation|compileOnly|runtimeOnly)\\s+\\s*group:\\s+\\'(.*)\\',\\s+name:\\s+\\'(.*)\\'";
                Pattern pattern1 = Pattern.compile(regex1);
                Pattern pattern2 = Pattern.compile(regex2);
                Pattern pattern3 = Pattern.compile(regex3);
                Pattern pattern4 = Pattern.compile(regex4);
                Pattern pattern5 = Pattern.compile(regex5);
                Pattern pattern6 = Pattern.compile(regex6);
                Matcher matcher1 = pattern1.matcher(line);
                Matcher matcher2 = pattern2.matcher(line);
                Matcher matcher3 = pattern3.matcher(line);
                Matcher matcher4 = pattern4.matcher(line);
                Matcher matcher5 = pattern5.matcher(line);
                Matcher matcher6 = pattern6.matcher(line);
                if (matcher1.find()) {
                    String group = matcher1.group(2);
                    String name = matcher1.group(3);
                    String version = matcher1.group(4);
                    if (version == null) {
                        version = "";
                    }
                    boolean isForTesting = matcher1.group(1).contains("test");
                    Dependency dependency = new Dependency(group, name, version, isForTesting);
                    dependencies.add(dependency);
                } else if (matcher2.find()) {
                    String group = matcher2.group(2);
                    String name = matcher2.group(3);
                    String version = "";
                    boolean isForTesting = matcher2.group(1).contains("test");
                    Dependency dependency = new Dependency(group, name, version, isForTesting);
                    dependencies.add(dependency);
                } else if (matcher3.find()) {
                    String group = matcher3.group(2);
                    String name = matcher3.group(3);
                    String version = matcher3.group(4);
                    if (version == null) {
                        version = "";
                    }
                    boolean isForTesting = matcher3.group(1).contains("test");
                    Dependency dependency = new Dependency(group, name, version, isForTesting);
                    dependencies.add(dependency);
                } else if (matcher4.find()) {
                    String group = matcher4.group(2);
                    String name = matcher4.group(3);
                    String version = "";
                    boolean isForTesting = matcher4.group(1).contains("test");
                    Dependency dependency = new Dependency(group, name, version, isForTesting);
                    dependencies.add(dependency);
                } else if (matcher5.find()) {
                    String group = matcher5.group(1);
                    String name = matcher5.group(2);
                    String version = matcher5.group(3);
                    if (version == null) {
                        version = "";
                    }
                    boolean isForTesting = matcher5.group(1).contains("test");
                    Dependency dependency = new Dependency(group, name, version, isForTesting);
                    dependencies.add(dependency);
                } else if (matcher6.find()) {
                    String group = matcher6.group(1);
                    String name = matcher6.group(2);
                    String version = "";
                    boolean isForTesting = matcher6.group(1).contains("test");
                    Dependency dependency = new Dependency(group, name, version, isForTesting);
                    dependencies.add(dependency);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dependencies;
    }

//    public static List<Dependency> parseDependencies(String filePath) {
//        List<Dependency> dependencies = new ArrayList<>();
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String regex = "^\\s*(compile|implementation|api|testCompile|testImplementation)\\s+\\'(.*):(.*):(.*)\\'";
//                Pattern pattern = Pattern.compile(regex);
//                Matcher matcher = pattern.matcher(line);
//                if (matcher.find()) {
//                    String group = matcher.group(2);
//                    String name = matcher.group(3);
//                    String version = matcher.group(4);
//                    boolean isForTesting = matcher.group(1).contains("test");
//                    Dependency dependency = new Dependency(group, name, version, isForTesting);
//                    dependencies.add(dependency);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return dependencies;
//    }

//    public static List<Dependency> parseDependencies(String buildGradleFile) {
//        List<Dependency> dependencies = new ArrayList<>();
//        ProjectConnection connection = GradleConnector.newConnector()
//                .forProjectDirectory(new File(buildGradleFile))
//                .connect();
//
//        ExternalDependency externalDependency = connection.model(ExternalDependency.class).get();
//        return dependencies;
//    }

    public static class Dependency {
        private final String group;
        private final String name;
        private final String version;
        private final boolean isForTesting;

        public Dependency(String group, String name, String version, boolean isForTesting) {
            this.group = group;
            this.name = name;
            this.version = version;
            this.isForTesting = isForTesting;
        }

        public String getGroup() {
            return group;
        }

        public String getName() {
            return name;
        }

        public String getVersion() {
            return version;
        }

        public boolean isForTesting() {
            return isForTesting;
        }
    }
}
