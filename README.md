# deck

This is just a Spring Boot Kotlin server to serve out a deck of 52 cards.

There should be support for REST endpoints and GraphQL endpoints.

You effectively "start" a session and then just reference the session from there.

In a session you can define if you want the jokers or not.

And you can do whatever you want with the "session" whether you want to:

1. Draw a card. 
2. Put a card back.
3. Add new cards (even if they duplicate what is in the deck already)