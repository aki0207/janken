package Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import sumple.Logic;
import sumple.Player;
import sumple.Player.Hand;

public class LogicTest {

	Player p1;
	Player p2;
	Player p3;
	List<Player> playerList;

	@Before
	public void setUp() {
		p1 = new Player("hoge");
		p2 = new Player("hogehoge");
		p3 = new Player("oge");
		playerList = Arrays.asList(p1, p2, p3);
		playerList.forEach(Player::selectHand);
	}

	@Test
	public void プレイヤーが全員同じ手ならtrue() {
		for (int i = 1; i < playerList.size(); i++) {
			while (p1.getHand() != playerList.get(i).getHand()) {
				playerList.get(i).selectHand();
			}
		}

		assertThat(Logic.isDraw(playerList), is(true));
	}

	@Test
	public void プレイヤーが全員違う手ならtrue() {
		settingHand(p1, Hand.STONE);
		settingHand(p2, Hand.PAPER);
		settingHand(p3, Hand.SCISSORS);

		assertThat(Logic.isDraw(playerList), is(true));
	}

	@Test
	public void プレイヤーの手の種類が2種類ならfalse() {
		settingHand(p1, Hand.STONE);
		settingHand(p2, Hand.STONE);
		settingHand(p3, Hand.SCISSORS);

		assertThat(Logic.isDraw(playerList), is(false));
	}

	@Test
	public void じゃんけんに勝った1人のプレイヤーを取得できる() {
		settingHand(p1, Hand.STONE);
		settingHand(p2, Hand.SCISSORS);
		settingHand(p3, Hand.SCISSORS);
		List<Player> winPlayerList =Logic.getJankenWinPlayerList(playerList);

		assertThat(winPlayerList.size(), is(1));
		assertThat(winPlayerList.get(0).getName(), is("hoge"));
	}

	@Test
	public void じゃんけんに勝った2人のプレイヤーを取得できる() {
		settingHand(p1, Hand.STONE);
		settingHand(p2, Hand.STONE);
		settingHand(p3, Hand.SCISSORS);
		List<Player> winPlayerList =Logic.getJankenWinPlayerList(playerList);

		assertThat(winPlayerList.size(), is(2));
		assertThat(winPlayerList.get(0).getName(), is("hoge"));
		assertThat(winPlayerList.get(1).getName(), is("hogehoge"));
	}

	@Test
	public void チョキのプレイヤーが存在する場合trueを取得する() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		settingHand(p2, Hand.SCISSORS);
		settingHand(p3, Hand.STONE);
		List<Player> otherPlayerList = Arrays.asList(p2, p3);
		Method method = Logic.class.getDeclaredMethod("isExistsLosingToMePlayer", List.class, Predicate.class);
		method.setAccessible(true);
		Predicate<Hand> conditions = Hand.SCISSORS::equals;

		assertThat(method.invoke(new Logic(), otherPlayerList, conditions), is(true));

	}

	@Test
	public void チョキのプレイヤーが存在しない場合falseを取得する() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		settingHand(p2, Hand.STONE);
		settingHand(p3, Hand.STONE);
		List<Player> otherPlayerList = Arrays.asList(p2, p3);
		Method method = Logic.class.getDeclaredMethod("isExistsLosingToMePlayer", List.class, Predicate.class);
		method.setAccessible(true);
		Predicate<Hand> conditions = Hand.SCISSORS::equals;

		assertThat(method.invoke(new Logic(), otherPlayerList, conditions), is(false));

	}

	@Test
	public void 勝利数が2のプレイヤーを取得する() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		p1.incrementWinCount();
		p1.incrementWinCount();
		Optional<Player> winPlayer = Logic.getTwoWinOrMorePlayer(playerList);

		assertThat(winPlayer.get().getName(), is("hoge"));
	}

	@Test
	public void 勝利数が2のプレイヤーが存在しない場合プレイヤーを取得しない() {
		Optional<Player> winPlayer = Logic.getTwoWinOrMorePlayer(playerList);
		assertThat(winPlayer.isEmpty(), is(true));
	}

	@Test
	public void 勝利数が1のプレイヤーを1人取得する() {
		p1.incrementWinCount();
		List<Player> winPlayerList = Logic.getOneWinPlayerList(playerList);

		assertThat(winPlayerList.size(), is(1));
		assertThat(winPlayerList.get(0).getName(), is("hoge"));
	}

	@Test
	public void 勝利数が1のプレイヤーを2人取得する() {
		p1.incrementWinCount();
		p2.incrementWinCount();
		List<Player> winPlayerList = Logic.getOneWinPlayerList(playerList);

		assertThat(winPlayerList.size(), is(2));
		assertThat(winPlayerList.get(0).getName(), is("hoge"));
		assertThat(winPlayerList.get(1).getName(), is("hogehoge"));
	}

	@Test
	public void 勝利数が1のプレイヤーが存在しない場合プレイヤーを取得しない() {
		List<Player> winPlayerList = Logic.getOneWinPlayerList(playerList);
		assertThat(winPlayerList.size(), is(0));
	}

	@Test
	public void 名前が重複している1人のプレイヤー取得する() {
		Player p4 = new Player("buzz");
		Player p5 = new Player("buzz");
		Player p6 = new Player("fizz");
		Set<Player> duplicationNamePlayerList = Logic.getDuplicationNamePlayerList(Arrays.asList(p4, p5, p6));
		String name = duplicationNamePlayerList.stream()
				.findFirst()
				.get()
				.getName();

		assertThat(duplicationNamePlayerList.size(), is(1));
		assertThat(name, is("buzz"));
	}

	@Test
	public void 名前が重複している2人のプレイヤー取得する() {
		Player p4 = new Player("buzz");
		Player p5 = new Player("buzz");
		Player p6 = new Player("fizz");
		Player p7 = new Player("jazz");
		Player p8 = new Player("fizz");

		Set<Player> duplicationNamePlayerList = Logic.getDuplicationNamePlayerList(Arrays.asList(p4, p5, p6, p7, p8));
		Iterator iterator = duplicationNamePlayerList.iterator();
		Player retP1 =  (Player) iterator.next();
		Player retP2 =  (Player) iterator.next();

		assertThat(duplicationNamePlayerList.size(), is(2));
		assertThat(retP1.getName(), is("buzz"));
		assertThat(retP2.getName(), is("fizz"));
	}

	public void settingHand(Player p, Hand hand) {
		for (;;) {
			if (hand == p.getHand()) {
				return;
			} else {
				p.selectHand();
			}
		}
	}
}
