# deck

This is just a Spring Boot Kotlin server to serve out a deck of 52 cards.

There should be support for REST endpoints and GraphQL endpoints.

You effectively "start" a session and then just reference the session from there.

In a session you can define if you want the jokers or not.

And you can do whatever you want with the "session" whether you want to:

1. Draw a card. 
2. Put a card back.
3. Add new cards (even if they duplicate what is in the deck already)

# REST endpoints

The following endpoints should be supported:

POST `/sessions/` + body of `{"jokers": false}`
^ this should create a new 52 card session

PUT `/sessions/ID:draw`
^ this should remove the first card and return it

PUT `/sessions/ID:shuffle`
^ this should reorder the cards in the deck

POST `/sessions/ID:add` + body of `[{"type": TYPE, "suit": SUIT}]`
^ this should add a set of cards to the deck, note that no uniqueness check is done
PS: can be used for magic tricks :P

# Graphql endpoints

There are some graphql queries, but they are not completely supported at the moment.

To visit the graphql editor, you can visit: 

`/graphiql`

And it should take you to the graphql interface