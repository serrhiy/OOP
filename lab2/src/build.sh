javac --module-path /home/serhii/programming/code/java/libs/javafx/lib --add-modules=javafx.controls,javafx.fxml Main.java controllers/MenuController.java shapes/Elipse.java shapes/Line.java shapes/Rectangle.java shapes/Shape.java editors/Editor.java editors/LineEditor.java
java --module-path /home/serhii/programming/code/java/libs/javafx/lib --add-modules=javafx.controls,javafx.fxml Main
rm *.class ./shapes/*.class ./controllers/*.class ./editors/*.class