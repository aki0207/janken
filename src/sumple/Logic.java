package sumple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import sumple.Player.Hand;

public class Logic {

	public static boolean isDraw(List<Player> playerList) {

		long handPattern = playerList.stream()
				.map(Player::getHand)
				.distinct()
				.count();

		// 全て同じ手または全ての種類の手が出ている場合引き分け
		return handPattern == 1 || handPattern == 3;
	}

	public static List<Player> getJankenWinPlayerList(List<Player> playerList) {

		List<Player> winPlayerList = new ArrayList<Player>();

		for (Player player : playerList) {

			final String  playerName = player.getName();
			List<Player> otherPlayerList = playerList.stream()
					.filter(s -> s.getName() != playerName)
					.collect(Collectors.toList());

			if (isSelectedStone(player)) {
				if (isExistsLosingToMePlayer(otherPlayerList, Hand.SCISSORS::equals)) {
					winPlayerList.add(player);
				}

			} else if (isSelectedScissors(player)) {
				if (isExistsLosingToMePlayer(otherPlayerList, Hand.PAPER::equals)) {
					winPlayerList.add(player);
				}

			} else {
				if (isExistsLosingToMePlayer(otherPlayerList, Hand.STONE::equals)) {
					winPlayerList.add(player);
				}
			}
		}

		return winPlayerList;

	}

	private static boolean isSelectedStone(Player player) {
		return Hand.STONE == player.getHand();
	}

	private static boolean isSelectedScissors(Player player) {
		return Hand.SCISSORS == player.getHand();
	}

	private static boolean isExistsLosingToMePlayer(List<Player> otherPlayerList, Predicate<Hand> conditions) {
		return otherPlayerList.stream()
				.map(Player::getHand)
				.anyMatch(conditions);
	}

	public static Optional<Player> getTwoWinOrMorePlayer(List<Player> playerList) {
		return  playerList.stream()
				.filter(s -> s.getWinCount() >= 2)
				.findFirst();
	}

	public static List<Player> getOneWinPlayerList(List<Player> playerList) {
		return playerList.stream()
				.filter(s -> s.getWinCount() == 1)
				.collect(Collectors.toList());
	}

	public static Set<Player> getDuplicationNamePlayerList(List<Player> playerList) {

		// コメントアウトしてるのも動作するが、Playerクラスでequalsメソッドのオーバライドでnameが等しければ
		// trueとみなしているため動作する
		//				return playerList.stream()
		//						.filter(s -> Collections.frequency(playerList, s) > 1)
		//						.collect(Collectors.toSet());
		Set<String> list = new HashSet<>();
		return playerList.stream()
				.filter(s -> !list.add(s.getName()))
				.collect(Collectors.toSet());
	}

	public static boolean isAppropriateNumberOfPeaple(List<Player> playerList) {
		return playerList.size() > 1 && playerList.size() < 6;
	}

}
