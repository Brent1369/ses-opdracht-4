module be.kuleuven.candycrushopdracht6 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;


    requires be.kuleuven.candycrush;


    //requires be.kuleuven.candycrush;




    opens be.kuleuven.candycrushopdracht6 to javafx.fxml;
    exports be.kuleuven.candycrushopdracht6;
}