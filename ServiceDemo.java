
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//workaround for problems where class containing
//main method can't extend Application.  Use 
//NewMain as main class
class NewMain {
	public static void main(String[] args) {
		ServiceDemo.main(args);
	}
}

public class ServiceDemo extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Service Class Demo");
		primaryStage.setHeight(480);
		primaryStage.setWidth(600);
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		Label label0 = new Label();
		Label label1 = new Label();
		vbox.getChildren().add(label0);
		vbox.getChildren().add(label1);
		Scene scene = new Scene(vbox, 400, 200);
		primaryStage.setScene(scene);
		primaryStage.show();

		Service0 service0 = new Service0();
		Service1 service1 = new Service1();

//////////////////////////////////////////////////////////////////////////////
		service0.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {
				label0.setText(Shared.msg0);

			}
		});
//////////////////////////////////////////////////////////////////////////////
		service1.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {
				label1.setText(Shared.msg1);
			}
		});

		label0.setText("Service 0 Running");
		service0.start();

		label1.setText("Service 1 Running");
		service1.start();

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}

class Shared {
	static String msg0, msg1;
}

//////////////////////////////////////////////////////////////////////////////
class Service0 extends Service<Void> {
	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				// simulate some work
				Thread.sleep(5000);
				Shared.msg0 = "Service 0 Completed";
				return null;
			}
		};
	}
}

//////////////////////////////////////////////////////////////////////////////
class Service1 extends Service<Void> {
	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				// simulate some work
				Thread.sleep(10000);
				Shared.msg1 = "Service 1 Completed";
				return null;
			}
		};
	}
}
