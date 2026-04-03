STUDENT GRADE MANAGER - OOP2 Assignment
=========================================
Student: Absar Ahammad Shaik
Student ID: A00336136
Module: Object Oriented Programming 2
TUS - Technological University of the Shannon

REQUIREMENTS
============
- Java 25 (Early Access) with preview features enabled
- Download from: https://jdk.java.net/25/

COMPILATION & RUNNING
=====================

Option A - JEP 512 Compact Source (Recommended):
  cd src/main/java
  java --enable-preview --source 25 ExamTrackerApp.java

Option B - Traditional Compilation:
  cd src/main/java
  javac --enable-preview --source 25 -d ../../../out com/gradetracker/model/*.java com/gradetracker/service/*.java com/gradetracker/util/*.java com/gradetracker/ui/*.java com/gradetracker/Main.java
  cp -r ../resources/* ../../../out/
  cd ../../../out
  java --enable-preview -cp . com.gradetracker.Main

FEATURES DEMONSTRATED
=====================

Fundamentals (8):
  F1 - Sorting: Comparator.comparing(), thenComparing(), reversed()
  F2 - Lambdas: Consumer, Predicate, Supplier, Function
  F3 - Streams Terminal: min, max, count, findFirst, findAny, allMatch, anyMatch, noneMatch
  F4 - Collectors: toMap, groupingBy, partitioningBy, averagingDouble
  F5 - Streams Intermediate: filter, distinct, limit, map, sorted
  F6 - Switch Expressions + Pattern Matching (guarded patterns on sealed types)
  F7 - Sealed Classes: Assessment sealed interface with Exam, Assignment, LabWork records
  F8 - Date/Time API: LocalDate, LocalTime, Period, Duration, DateTimeFormatter

Advanced (5):
  A1 - Concurrency: ExecutorService, Callable, Future, invokeAll
  A2 - NIO2: Files.write, readAllLines, walk, copy, Paths, createDirectories
  A3 - Localisation: ResourceBundle (en, ga_IE, fr), Locale, NumberFormat
  A4 - JEP 512: Compact Source Files + Instance Main Method
  A5 - JEP 513: Flexible Constructor Bodies (pre-super statements)

Extra Credit (2):
  E1 - Scoped Values (JEP 487)
  E2 - Stream Gatherers (JEP 485)

PROJECT STRUCTURE
=================
src/main/java/
  ExamTrackerApp.java                          - A4: JEP 512 entry point
  com/gradetracker/
    Main.java                                  - Traditional entry point
    model/
      Assessment.java                          - F7: Sealed interface
      Exam.java                                - F7: Record
      Assignment.java                          - F7: Record
      LabWork.java                             - F7: Record
      Student.java                             - Record
      Module.java                              - Record
      Result.java                              - Record
      Grade.java                               - Enum with fromScore()
      Semester.java                            - Enum
      SampleData.java                          - Test data
    service/
      GradeReportService.java                  - F1: Sorting
      AcademicAnalyticsService.java            - F3/F4/F5: Streams
      ExamScheduleService.java                 - F8: Date/Time API
      ResultsProcessingService.java            - A1: Concurrency
      AcademicDataIO.java                      - A2: NIO2
      LocaleService.java                       - A3: Localisation
      BaseTracker.java                         - A5: JEP 513 base class
      TrackerManager.java                      - A5: JEP 513 subclass
    util/
      StudentLambdaUtils.java                  - F2: Lambdas
      AssessmentProcessor.java                 - F6: Switch + Pattern Matching
      TrackerContext.java                      - E1: Scoped Values
      AcademicGatherers.java                   - E2: Stream Gatherers
    ui/
      MenuHandler.java                         - Interactive menu
src/main/resources/
  messages.properties                          - Default (English)
  messages_en.properties                       - English
  messages_ga_IE.properties                    - Irish (Gaeilge)
  messages_fr.properties                       - French
