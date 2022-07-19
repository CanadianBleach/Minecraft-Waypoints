package commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.beans.ParameterDescriptor;
import java.util.ArrayList;

// Waypoint class to hold data for position and waypoint name
class Waypoint {
    // Variables
    private Location location;
    private String name;

    // Constructor
    public Waypoint(Location location, String name) {
        this.location = location;
        this.name = name;
    }

    // Getters and setters
    public void ChangeName(String name) {
        this.name = name;
    }
    public void ChangeLocation(Location location) {
        this.location = location;
    }
    public String getName() {
        return name;
    }
    public Location getLocation() {
        return location;
    }
}

// Command Class
public class WaypointCommand implements CommandExecutor {

    // List of waypoint classes to track waypoints
    public ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();

    public void tellConsole(String message){
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public String LocationToString(Location location) {
        String locationString = "";
        locationString += location.getX() + ", " + location.getY() + ", " + location.getZ();

        return locationString;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        // If player is console return
        if (!(sender instanceof Player)) {
            return true;
        }

        // Convert sender to player
        Player player = (Player) sender;

        // If the command is this one
        if (cmd.getName().equalsIgnoreCase("wp")) {
            String name = "";

            // Check if there is a name
            if (args.length == 0) {
                tellConsole("You need to specify a name!");
                return true;
            }
            // Setup name string
            else {
                for (int i = 1; i<args.length; i++) {
                    name += args[i] + " ";
                }
            }

            // Make sure no other waypoint has this name
            for (Waypoint w : waypoints) {
                if(w.getName().equalsIgnoreCase(name)) {
                    tellConsole("There is already a waypoint with this name!");
                    return true;
                }
            }

            // Add waypoint
            Waypoint wp = new Waypoint(player.getLocation(), name);
            waypoints.add(wp);
            tellConsole("Waypoint " + name + "added at position, " + LocationToString(wp.getLocation()));
        }

        // Delete waypoints
        if (cmd.getName().equalsIgnoreCase("delete_wp")) {
            String name = "";

            // Check if there is a name
            if (args.length == 0) {
                tellConsole("You need to specify a waypoint to delete!");
                return true;
            }
            // Setup name string
            else {
                for (int i = 1; i<args.length; i++) {
                    name += args[i] + " ";
                }
            }

            // Delete waypoint
            for (Waypoint w : waypoints) {
                if(w.getName().equalsIgnoreCase(name)) {
                    waypoints.remove(w);
                    tellConsole("Waypoint Removed!");
                    return true;
                }
            }

            tellConsole("No waypoint with that name was found!");
            return true;
        }

        // Show Waypoints
        if (cmd.getName().equalsIgnoreCase("show_wp")) {
            // Loop and print
            for (Waypoint w : waypoints) {
                tellConsole("Waypoint: " + w.getName() + "is at location " + w.getLocation());
            }

            return true;
        }

        return true;
    }
}
