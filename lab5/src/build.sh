javac --module-path /home/serhii/programming/code/java/libs/javafx/lib --add-modules=javafx.controls,javafx.fxml,javafx.swing Main.java controllers/MenuController.java controllers/TableController.java
java --module-path /home/serhii/programming/code/java/libs/javafx/lib --add-modules=javafx.controls,javafx.fxml,javafx.swing Main
find . -name '*.class' -exec rm {} \;