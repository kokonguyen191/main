package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_PREPARATION_TIME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;

/**
 * Adds a recipe to the recipe book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a recipe to the recipe book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PREPARATION_TIME + "PREPARATION_TIME "
            + PREFIX_INGREDIENT + "INGREDIENT "
            + PREFIX_INSTRUCTION + "INSTRUCTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PREPARATION_TIME + "98765432 "
            + PREFIX_INGREDIENT + "johnd@example.com "
            + PREFIX_INSTRUCTION + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New recipe added: %1$s";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe already exists in the recipe book";

    private final Recipe toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Recipe}
     */
    public AddCommand(Recipe recipe) {
        requireNonNull(recipe);
        toAdd = recipe;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addRecipe(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateRecipeException e) {
            throw new CommandException(MESSAGE_DUPLICATE_RECIPE);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}