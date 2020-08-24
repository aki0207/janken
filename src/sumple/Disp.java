package sumple;

import java.util.List;
import java.util.Set;

import sumple.Player.Hand;

public class Disp {

	public static void playersHand(List<Player> playerList) {
		playerList.stream()
		.peek(s -> System.out.print(s.getName() + "の手:"))
		.map(Player::getHand)
		.map(Hand::getName)
		.forEach(System.out::println);
	}

	public static void winCountAndName(Player winPlayer) {
		System.out.println("勝者:" + winPlayer.getName());
		System.out.println("勝った回数:" + winPlayer.getWinCount());
	}

	public static void draw(List<Player> oneWinPlayerList, int winCount) {
		System.out.println("以下のプレイヤーが勝利数" + winCount + "でドロー");
		oneWinPlayerList.stream()
		.map(Player::getName)
		.forEach(System.out::println);
	}

	public static void jankenWinPlayersName(List<Player> playerList) {
		System.out.println("以下のプレイヤーが勝ちました");
		playerList.stream()
		.map(Player::getName)
		.forEach(System.out::println);
	}

	public static void duplicationPlayersName(Set<Player> playerList) {
		System.out.println("以下のユーザー名が重複しているためシステムを終了します");
		playerList.stream()
		.map(Player::getName)
		.forEach(System.out::println);
	}

	public static void improperNumberOfPeople() {
		System.out.println("プレイヤー数が不正です");
		System.out.println("プレイヤー数は2人以上5人以下です");
	}
}
