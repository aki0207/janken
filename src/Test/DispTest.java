package Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sumple.Disp;
import sumple.Player;

public class DispTest {

	ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	PrintStream originalOut =  System.out;
	String lineSeparator = System.getProperty("line.separator");


	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void restoreStream() {
		System.setOut(originalOut);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void プレイヤー2人の手が表示される() {
		Player p1 = new Player("hoge");
		Player p2 = new Player("hogehoge");
		p1.selectHand();
		p2.selectHand();
		List<Player> playerList = Arrays.asList(p1, p2);

		Disp.playersHand(playerList);

		String stoneAndStone = "hogeの手:グー" + lineSeparator + "hogehogeの手:グー" + lineSeparator;
		String stoneAndPaper = "hogeの手:グー" + lineSeparator + "hogehogeの手:パー" + lineSeparator;
		String stoneAndScissors = "hogeの手:グー" + lineSeparator + "hogehogeの手:チョキ" + lineSeparator;
		String paperAndPaper = "hogeの手:パー" + lineSeparator + "hogehogeの手:パー" + lineSeparator;
		String paperAndScissors = "hogeの手:パー" + lineSeparator + "hogehogeの手:チョキ" + lineSeparator;
		String paperAndStone = "hogeの手:パー" + lineSeparator + "hogehogeの手:グー" + lineSeparator;
		String scissorsAndScissors = "hogeの手:チョキ" + lineSeparator + "hogehogeの手:チョキ" + lineSeparator;
		String scissorsAndStone = "hogeの手:チョキ" + lineSeparator + "hogehogeの手:グー" + lineSeparator;
		String scissorsAndPaper = "hogeの手:チョキ" + lineSeparator + "hogehogeの手:パー" + lineSeparator;

		assertThat(outContent.toString(),
				anyOf(equalTo(stoneAndStone), equalTo(stoneAndPaper), equalTo(stoneAndScissors),
						equalTo(paperAndPaper), equalTo(paperAndScissors), equalTo(paperAndStone),
						equalTo(scissorsAndScissors), equalTo(scissorsAndStone), equalTo(scissorsAndPaper)
						));
	}

	@Test
	public void プレイヤーの名前と勝利数が表示される() {
		Player p1 = new Player("hoge");
		p1.incrementWinCount();
		p1.incrementWinCount();
		Disp.winCountAndName(p1);

		String expected = "勝者:hoge" + lineSeparator + "勝った回数:2" + lineSeparator;
		assertThat(outContent.toString(), is(expected));
	}

	@Test
	public void プレイヤー2人の名前と勝利数が表示される() {
		Player p1 = new Player("hoge");
		Player p2 = new Player("hogehoge");
		p1.incrementWinCount();
		p2.incrementWinCount();
		List<Player> playerList = Arrays.asList(p1, p2);
		Disp.draw(playerList, 1);

		String expected = "以下のプレイヤーが勝利数1でドロー" + lineSeparator +
				"hoge" + lineSeparator +
				"hogehoge" + lineSeparator;
		assertThat(outContent.toString(), is(expected));
	}

	@Test
	public void プレイヤー2人の名前が表示される() {
		Player p1 = new Player("hoge");
		Player p2 = new Player("hogehoge");
		List<Player> playerList = Arrays.asList(p1, p2);
		Disp.jankenWinPlayersName(playerList);

		String expected = "以下のプレイヤーが勝ちました" + lineSeparator +
				"hoge" + lineSeparator +
				"hogehoge" + lineSeparator;
		assertThat(outContent.toString(), is(expected));
	}

	@Test
	public void 名前が重複しているプレイヤーの名前が表示される() {
		Player p1 = new Player("hoge");
		Set<Player> playerList = new HashSet<>();
		playerList.add(p1);
		Disp.duplicationPlayersName(playerList);

		String expected = "以下のユーザー名が重複しているためシステムを終了します" + lineSeparator +
				"hoge" + lineSeparator;
		assertThat(outContent.toString(), is(expected));
	}



}
