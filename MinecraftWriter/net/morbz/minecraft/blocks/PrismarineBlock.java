package net.morbz.minecraft.blocks;

/**
 * @author verbuchselt
 */
public enum PrismarineBlock implements IBlock {

    PRISMARINE( (byte) 0 ),
    PRISMARINE_BRICK( (byte) 1 ),
    DARK_PRISMARINE( (byte) 2 );

    PrismarineBlock( byte data ) {
        this.data = data;
    }

    private byte data;

    @Override
    public int getBlockId() {
        return Material.MATERIALS.get("PRISMARINE").getValue();
    }

    @Override
    public byte getBlockData() {
        return data;
    }

    @Override
    public int getTransparency() {
        return (byte) Material.MATERIALS.get("PRISMARINE").getValue();
    }

}
