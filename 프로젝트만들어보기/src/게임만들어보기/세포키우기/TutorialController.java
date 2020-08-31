package 게임만들어보기.세포키우기;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TutorialController implements Initializable {
	@FXML private AnchorPane AP;
	@FXML private Label back;
	@FXML private Circle player;
	@FXML private Scene scene;

	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;

	private static final double W = 900;
	private static final double H = 600;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		back.setOnMouseClicked(e -> handleBackBtn(e));

		AP.setOnMouseEntered(e -> ActionToScene());

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				int dx = 0, dy = 0;

				if (up)
					dy -= 1;
				if (down)
					dy += 1;
				if (left)
					dx -= 1;
				if (right)
					dx += 1;

				movePlayerBy(dx, dy);
			}
		};
		timer.start();

	}

	private void ActionToScene() {
		scene = AP.getScene();

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
				case W:
					up = true;
					break;
				case DOWN:
				case S:
					down = true;
					break;
				case LEFT:
				case A:
					left = true;
					break;
				case RIGHT:
				case D:
					right = true;
					break;
				default:
					break;
				}
			};
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
				case W:
					up = false;
					break;
				case DOWN:
				case S:
					down = false;
					break;
				case LEFT:
				case A:
					left = false;
					break;
				case RIGHT:
				case D:
					right = false;
					break;
				default:
					break;
				}
			};
		});
	}

	private void movePlayerBy(int dx, int dy) {
		if (dx == 0 && dy == 0)
			return;

		final double cx = player.getBoundsInLocal().getWidth() / 2 * dx;
		final double cy = player.getBoundsInLocal().getHeight() / 2 * dy;
		double x = cx + player.getLayoutX() + dx;
		double y = cy + player.getLayoutY() + dy;

		movePlayerTo(x, y);
	}

	private void movePlayerTo(double x, double y) {
		final double cx = player.getBoundsInLocal().getWidth() / 2;
		final double cy = player.getBoundsInLocal().getHeight() / 2;

		if (x - cx >= 0 && x + cx <= W && 
			y - cy >= 0 && y + cy <= H) {

			player.relocate(x - cx, y - cy);
		} else if(x - cx <= 0) {
			if(y - cy >= 0 && y + cy <= H) {
				player.relocate(cx, y - cy);
			}
		} else if(x + cx >= W) {
			if(y - cy >= 0 && y + cy <= H) {
				player.relocate(W - cx, y - cy);
			}
		} else if(y - cy <= 0) {
			if(x - cx >= 0 && x + cx <= W) {
				player.relocate(x - cx, cy);
			}
		} else if(y + cy >= H) {
			if(x - cx >= 0 && x + cx <= W) {
				player.relocate(x - cx, H - cy);
			}
		}
	}

	public void handleBackBtn(MouseEvent e) {
		try {
			StackPane root = (StackPane) back.getScene().getRoot();
			root.getChildren().remove(AP);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
