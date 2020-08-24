package sumple;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

public class Main {

	public static void main(String[] args) {

		// プレーヤー数は2〜5人まで自由に増減できる仕様
		// ただし、名前の重複は許さない
		Player p1 = new Player("ゆーのー");
		Player p2 = new Player("むのー");
		Player p3 = new Player("ふつー");

		List<Player> playerList = Arrays.asList(p1, p2, p3);
		// 名前の重複があれば終了
		Set<Player> duplicationNamePlayerList = Logic.getDuplicationNamePlayerList(playerList);
		if (duplicationNamePlayerList.size() != 0) {
			Disp.duplicationPlayersName(duplicationNamePlayerList);
			return;
		}

		// プレイヤー数が2〜5人以外なら終了
		if (!Logic.isAppropriateNumberOfPeaple(playerList)) {
			Disp.improperNumberOfPeople();
			return;
		}

		System.out.println("じゃんけんを開始するぜよ");

		// じゃんけんは3回勝負
		// for文で十分やけど使ってみた
		IntStream.rangeClosed(1, 3).forEach(i -> {

			System.out.println("[" + i + "回戦]");
			playerList.forEach(Player::selectHand);
			Disp.playersHand(playerList);

			// あいこの際は引き分けとして次回のじゃんけんに移行する
			if (Logic.isDraw(playerList)) {
				System.out.println("引き分け");
				return;
			}

			List<Player> winPlayerList = Logic.getJankenWinPlayerList(playerList);
			Disp.jankenWinPlayersName(winPlayerList);
			winPlayerList.forEach(Player::incrementWinCount);

		});

		// 2勝以上しているプレイヤーがいればそのプレイヤーが確実に勝者となる
		Optional<Player> winPlayer = Logic.getTwoWinOrMorePlayer(playerList);

		if (winPlayer.isPresent()) {
			Disp.winCountAndName(winPlayer.get());
			return;
		}

		List<Player> oneWinPlayerList = Logic.getOneWinPlayerList(playerList);

		// 1勝のプレイヤーが１人ならそのプレイヤーの勝利
		if (oneWinPlayerList.size() == 1) {
			Disp.winCountAndName(oneWinPlayerList.get(0));

		} else if (oneWinPlayerList.size() > 1) {
			// 1勝のプレイヤーは複数存在する可能性がある
			Disp.draw(oneWinPlayerList, 1);

		} else {
			// 0勝のプレイヤーしか存在しない場合
			Disp.draw(playerList, 0);
		}
	}
}