package ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.�̹������ε�.RoadTile;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RoadTile extends ImageView{
	public RoadTile(int area) {
		Image image = new Image(getClass().getResource("RoadTile" + (int)(Math.random() * 4 + 1) +  ".png").toString());
		// Image image = new Image(getClass().getResource("���� Ÿ��.jpg").toString());
		super.setImage(image);
		super.setFitHeight(area);
		super.setFitWidth(area);
	}
}
