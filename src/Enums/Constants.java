package Enums;

/**
 * Created by vitorchagas on 22/09/16.
 *
 * Enum that contains the constants used in the program.
 */
public enum Constants {

    TIME_SECOND(1000);

    private int value;

    Constants(int value) { this.value = value; }

    public int getValue() { return this.value; }
}
