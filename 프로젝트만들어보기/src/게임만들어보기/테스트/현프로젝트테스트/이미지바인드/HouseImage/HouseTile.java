package ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.�̹������ε�.HouseImage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HouseTile extends ImageView {
	public HouseTile(int area) {
		int random = (int)(Math.random() * 6 + 1);
		Image image = new Image(getClass().getResource("��" + random +  ".png").toString());
		
		double height = 0.0;
		
		if(random == 2)
			height = (double)area / 3 * 2;
		else
			height = (double)area / 3 * 2 + 10;
		
		super.setImage(image);
		super.setFitHeight(height);
		super.setFitWidth(area + 10);
	}
}
