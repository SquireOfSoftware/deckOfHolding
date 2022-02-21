package deck.deck

enum class Suit(
    val icon: Char,
    val id: String
) {
    Clubs('♣', "clubs"),
    Diamonds('♦', "diamonds"),
    Hearts('♥', "hearts"),
    Spades('♠', "spades"),
    None(' ', "none")
}