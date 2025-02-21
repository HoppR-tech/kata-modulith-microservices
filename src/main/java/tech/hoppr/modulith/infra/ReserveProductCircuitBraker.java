package tech.hoppr.modulith.infra;

public class ReserveProductCircuitBraker {
    private Boolean open;

    public void Open() {
        this.open = true;
        this.CheckCloseCircuitBraker();
    }

    private void Close() {
        this.open = true;
        this.CheckCloseCircuitBraker();
    }

    private void CheckCloseCircuitBraker() {
        // Sleep 
        try {
            // Http Call to Inventory
        }
        catch(Exception e) {
            this.Close();
        }
    }
}
