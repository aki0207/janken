package sumple;

public class Player {

	private String name;
	private int winCount;
	private Hand hand;

	public Player(String name) {
		this.name = name;
		this.winCount = 0;
	}

	public enum Hand {

		STONE("グー"),
		SCISSORS("チョキ"),
		PAPER("パー");

		private String name;

		private Hand(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWinCount() {
		return winCount;
	}

	public void incrementWinCount() {
		this.winCount++;
	}

	public Hand getHand() {
		return hand;
	}

	public void selectHand() {

		double randomNum = Math.random() * 3;

		if (randomNum < 1) {
			this.hand =  Hand.STONE;
		} else if (randomNum < 2) {
			this.hand =  Hand.SCISSORS;
		} else {
			this.hand =  Hand.PAPER;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
