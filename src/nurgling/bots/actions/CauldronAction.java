package nurgling.bots.actions;

import haven.Area;
import haven.GItem;

import haven.Gob;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;

import static nurgling.NGob.*;


public class CauldronAction implements Action {
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        if(cauldron == null){
            new UseWorkStation ( new NAlias ( "cauldron" ), "Cauldron", "Open" ).run ( gui );
        }else{
            new UseWorkStation ( cauldron, new NAlias("cauldron"), "Cauldron", "Open" ).run ( gui );
        }
        
        while ( !gui.getInventory( "Cauldron" ).getWItems ( iitems ).isEmpty () &&
                ( getModelAttribute ( Finder.findObject ( new NAlias ( "cauldron" )) ) & 2 ) != 0 &&
                ( getModelAttribute ( Finder.findObject ( new NAlias ( "cauldron" )) ) & 4 ) != 0 ) {
            Thread.sleep ( 1000 );
        }
        /*if ( ( getModelAttribute ( Finder.findObject ( new NAlias ( "cauldron" ) ) ) & 2 ) == 0 ||
                ( getModelAttribute ( Finder.findObject ( new NAlias ( "cauldron" ) ) ) & 4 ) == 0 ) {
            return new Results ( Results.Types.SUCCESS );
        }*/
        if ( !gui.getInventory ( "Cauldron" ).getWItems ( oitems ).isEmpty () ) {
            //todo: исключение на тип предмета, если это "пепел" а не готовый лай
            new TakeFromContainer ( "Cauldron", oitems, 9 ).run ( gui );
        }
        if ( toBarrel ) {
            new TransferToBarrel ( outut, oitems ).run ( gui );
        }
        else {
            new TransferItemsToContainers ( 16, outut, null, "", oitems ).run ( gui );
        }
        
        if ( fromBarrel ) {
            new TakeFromBarrels ( input, 9, iitems ).run ( gui );
        }
        else {
            new TakeFromContainers ( null, iitems, 9, input, "" ).run ( gui );
        }
        if(cauldron == null){
            new UseWorkStation ( new NAlias ( "cauldron" ), "Cauldron", "Open" ).run ( gui );
        }else{
            new UseWorkStation ( cauldron, new NAlias("cauldron"), "Cauldron", "Open" ).run ( gui );
        }
        new TransferToContainer ( iitems, "Cauldron" ).run ( gui );
        return new Results ( Results.Types.SUCCESS );
    }
    
    public CauldronAction (
            NAlias iitems,
            NAlias oitems,
            AreasID input,
            AreasID outut,
            boolean barrel
    ) {
        this.iitems = iitems;
        this.oitems = oitems;
        this.input = input;
        this.outut = outut;
        if ( barrel ) {
            toBarrel = true;
            fromBarrel = true;
        }
    }

    public CauldronAction (
            Gob cauldron,
            NAlias iitems,
            NAlias oitems,
            AreasID input,
            AreasID outut,
            boolean barrel
    ) {
        this.cauldron = cauldron;
        this.iitems = iitems;
        this.oitems = oitems;
        this.input = input;
        this.outut = outut;
        if ( barrel ) {
            toBarrel = true;
            fromBarrel = true;
        }
    }
    
    
    boolean toBarrel = false;
    boolean fromBarrel = false;
    NAlias iitems;
    NAlias oitems;
    AreasID input;
    AreasID outut;
    Gob cauldron = null;
    
}
