package cz.cvut.fel.arima_fx.Board;
public interface Board {
    /**
     * inicializované hrací pole na nativní hrací parametz
     *  bez rozestavění fugurek.
     */
    void creat_board();

    void set_board(int[][] coord_list);

    /**
     * @return datový typ Boolen ozanmující zda nějaký zajíc přešel celé pole.
     * null --> Žádný zajíc nepřešel pole
     * true --> White zajíc přešel
     * false --> Black zajíc přešel
     */
     Boolean did_bunny_cross();

    Boolean validate_isFree(Box box);

    boolean MOVE(Box box_from, Box box_to);

    void move_from_to(Box box_from, Box box_to);

    /**
     * funkce projde cele hrací pole
     * a nastaví atribut @Freeze na příslušnou hodnotu.
     */
    void check_freeze();

    /**
     * Funkce projde trap políčka a vymaže případné
     * figurky na poli
     */
    Object[] check_traps();

    /**
     * modifikuje pole boolean list_of_trap_safty , hodnota na pozici udává zda-li je trap nebezpečná pro danou barvu.
     * boool[x][0] ---> White
     * boool[x][1-> Black
     * IF True => box is trap
     */

    void set_trap_for();

    boolean MOVE_OPONENT(Box box_from, Box box_to);

    /**
     * Funkce validuje zda-li move byl legalni pull nebo push
     */
    boolean PULL (Box box_from, Box box_to);
    boolean PUSH (Box box_from, Box box_to);




}