package ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.������Ʈ;

public class CharacterPlayer{

	private static CharacterPlayer cp = new CharacterPlayer();
	
	private CharacterPlayer() {}
	
	static CharacterPlayer getInstance() {
		return cp;
	}
}
