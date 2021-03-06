package client.data;

/**
 * Used to pass player information into the rob view<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique player ID</li>
 * <li>PlayerIndex: Player's order in the game [0-3]</li>
 * <li>Name: Player's name (non-empty string)</li>
 * <li>Color: Player's color (cannot be null)</li>
 * <li>NumCards: Number of development cards the player has (&gt;= 0)</li>
 * </ul>
 * 
 */
public class RobPlayerInfo extends PlayerInfo
{
	
	private int numCards;
	
	public RobPlayerInfo()
	{
		super();
	}
	
	public RobPlayerInfo(PlayerInfo playerInfo) {
		super();
		setId(playerInfo.getId());
		setPlayerIndex(playerInfo.getPlayerIndex());
		setName(playerInfo.getName());
		setColor(playerInfo.getColor());
	}
	
	public int getNumCards()
	{
		return numCards;
	}
	
	public void setNumCards(int numCards)
	{
		this.numCards = numCards;
	}
}
