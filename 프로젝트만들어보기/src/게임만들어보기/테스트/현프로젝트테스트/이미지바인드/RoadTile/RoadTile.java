package 게임만들어보기.테스트.현프로젝트테스트.이미지바인드.RoadTile;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RoadTile extends ImageView{
	public RoadTile(int area) {
		Image image = new Image(getClass().getResource("RoadTile" + (int)(Math.random() * 4 + 1) +  ".png").toString());
		// Image image = new Image(getClass().getResource("도로 타일.jpg").toString());
		super.setImage(image);
		super.setFitHeight(area);
		super.setFitWidth(area);
	}
}
