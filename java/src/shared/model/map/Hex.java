package shared.model.map;

import java.io.Serializable;
import java.util.Objects;

import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;

/**
*location (HexLocation),
*resource (string, optional) = ['Wood' or 'Brick' or 'Sheep' or 'Wheat' or 'Ore']: What resource
*this tile gives - it's only here if the tile is not desert.,
*number (integer, optional): What number is on this tile. It's omitted if this is a desert hex.
*/
public class Hex implements Serializable{
    HexLocation location;
    ResourceType resource;
    int number;
    
    public Hex(HexLocation location, ResourceType resource, int number) {
            this.location = location;
            this.resource = resource;
            this.number = number;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.location);
        hash = 29 * hash + Objects.hashCode(this.resource);
        hash = 29 * hash + this.number;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hex other = (Hex) obj;
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (this.resource != other.resource) {
            return false;
        }
        if (this.number != other.number) {
            return false;
        }
        return true;
    }

    public HexLocation getLocation() {
        return location;
    }

    public void setLocation(HexLocation location) {
        this.location = location;
    }
    
    public ResourceType getResourceType(){
        return resource;
    }
    
    //Looks at the resource type and location of this hex to determine it's HexType
    public HexType getHexType()
    {
    	if ( resource == null ) {
            //Water or Desert
    		int x = location.getX();
    		int y = location.getY();
    		//Check for water coordinates
    		if (Math.abs(x) == 3 || Math.abs(y) == 3){
    			return HexType.WATER;
    		} else if (Math.abs(x + y) == 3) {
    			return HexType.WATER;
    		} else {
    			return HexType.DESERT;
    		}
        }

        switch (resource)
    	{
    	case WOOD:
    		return HexType.WOOD;
    	case BRICK:
    		return HexType.BRICK;
    	case SHEEP:
    		return HexType.SHEEP;
    	case WHEAT:
    		return HexType.WHEAT;
    	case ORE:
    		return HexType.ORE;
    	}
        return null;
    }
    
    public int getNumber() {
    	return number;
    }

    void setNumber(int theNum) {
        this.number = theNum;
    }
    
    
    
}
