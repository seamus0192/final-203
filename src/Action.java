import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * An action that can be taken by an entity
 */

// Peter's comment
public interface Action
{
    void executeAction(EventScheduler scheduler);
}
