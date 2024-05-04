public class StorageDevice extends Part {
    
    // StorageDevice has storageType
    private String storageType;
    
    // StorageDevice has storageTB
    private String storageTB;
    
    public StorageDevice(String[] parameters) {
        super(parameters);
        this.storageType = parameters[5];
        this.storageTB = parameters[6];
    }
    
    /**
     *
     */
    @Override
    protected String getInfo() {
        String info = super.getInfo();
        info += ",\nStorage Type: " + storageType + ",\nStorage (TB): " + storageTB;
        return info;
    }
}
