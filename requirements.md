# Songify: Application to manage Albums, Artists and Songs

1. Can Add artist (artist name)
2. Can add music genre (genre name)
3. can add album (title, releaseDate,but the album have to at least 1 song)
4. can add song(title,duration,releaseDate and the artist)
5. can delete artist ( it will delete artists album and songs as well)
6. can delete album (only possible when there is no songs left in the album)
7. can delete song
8. can edit music genre
9. can edit album(add songs,artists, album title)
10. can edit song(duration, artist, song name)
11. can get all artists 
12. can get all albums 
13. can get all genres 
14. can get all albums with artist and the songs in the particular album 
15. can get particular genre and songs 
16. can get artists with all their albums 
17. can attach songs to albums 
18. can attach artist to albums(albums can have multiple artists ,artists can have multiple albums)
19. one song can only have one genre attached

HAPPY PATH(user create album for artist "Eminem" with songs "Till I collapse", "Lose Yourself", genre RAP )
given there is no songs,artists,albums and genres created before

1. when I go to /songs -
2. When I post to /songs with "Till I collapse" then Song "Till I collapse" is returned with id 1
3. When I post to /songs with "Till I collapse" then Song "Till I collapse" is returned with id 2