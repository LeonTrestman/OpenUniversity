public class PieceWorkerEmployee extends Employee{
    private int numOfPieces;
    private double piecePrice;

    public PieceWorkerEmployee(String firstName, String lastName,
                               String socialSecurityNumber, int numOfPieces, double piecePrice,
                               int day, int month, int year){

        super(firstName, lastName, socialSecurityNumber,day, month, year);
        if (!isValidNumOfPieces(numOfPieces))
            throw new IllegalArgumentException("Number of pieces must be non negative");

        this.numOfPieces = numOfPieces;
        this.piecePrice = piecePrice;
    }

    @Override
    public String toString() {
        return String.format("piece worker employee: %s%nPiece price: $%,.2f%nNumber of pieces: %d",
                super.toString(), getPiecePrice(), getNumOfPieces());
    }

    public int getNumOfPieces() {
        return numOfPieces;
    }

    public double getPiecePrice() {
        return piecePrice;
    }

    //valid number of product is non negative
    private boolean isValidNumOfPieces(int num){
        return num >= 0;
    }

    @Override
    public double earnings() {
        return numOfPieces * piecePrice ;
    }


}
