import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * An action that can be taken by an entity
 */

// Perfect!!!!!!!
public interface Action
{
    void executeAction(EventScheduler scheduler);
}
