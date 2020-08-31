package 게임만들어보기.테스트.현프로젝트테스트.오브젝트;

public class CharacterPlayer{

	private static CharacterPlayer cp = new CharacterPlayer();
	
	private CharacterPlayer() {}
	
	static CharacterPlayer getInstance() {
		return cp;
	}
}
