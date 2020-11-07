*Project for the Politecnico di Milano course in Middleware Technologies for Distributed Systems*

# Board Game Manager

A service to help people keep track of their board games nights.

## Description

The goal of this project is to create a service that can help people keep track of their board games nights.

The Board Game Manager should provide a REST API for:

- creating new users (each user should have a unique id and a name)
- listing the users in the system (ordering/filtering by specific attributes)
- viewing a user's details
- adding new board games to an online database
- listing the board games available in the online database (ordering/filtering by specific attributes)
- viewing a board game's details (a board game will always have an id, a name, a list of designers, and a .jpg file of the board game's cover art)
- creating a new boardgame "play" (a play is associated to a specific user and to a specific game in the database, and contains a date. It can optionally also contain additional data such as the amount of time it took to complete the game, how many players were involved, and the id of the user that won the game, if applicable)
- viewing existing plays by user (ordering/filtering by board game and/or date)

The API should be developed in such a way as to support Hypermedia whenever suitable.
