# LoveFinderz
An application for single looking for love.

<hr />
# Abstract
A project of LoveFinderrz was designed as a Tinder-like application. Its main purposes is to:
- allow creating profiles and storing them in cloud database.
- allow rating other profiles.
- provide a protocol of sending rates and searching for matches between users.\\[2em]

<hr />
# Used tools
There was two main technologies used to develop this application. First one is the Firebase. It is used for back end functionality. Server uses two main services of Firebase: Authentication service used to store user credentials, Cloud Firestore used to store user profiles, their rates and temporal data used in protocol processing.

Second technology is the Android Studio - an environment used to develop front end functionality of application.

<hr />
# Business layers


## Register

Application gives an opportunity to create profiles using email-authentication. User has to provide their username, unique email address and password. After confirming, there is generated a request sent to Firebase Authentication to store user credentials (email address and generated hash of password) and request to Firestore to store all data - except of password - like username, date of birth, etc.

![alt text](https://github.com/wojtekrafalo/LoveFinderz/blob/master/docs/img/register_and_signIn.png)


## Sign in
There is a way to sign in an user stored in Authentication service. To perform this operation, user have to provide email address and password. An operation of registration on user side is presented in figure above.


## Profile browsing
Every user can rate other registered by using two options: *like* and *dislike*. After rating, specific one, application loads new profile as long, as specific user has not rated all users stored in database. Figure below shows a view of browsing functionality.

![alt text](https://github.com/wojtekrafalo/LoveFinderz/blob/master/docs/img/profile_browser.png)


## Matched users
There is a possibility to show all matched users with specific one.


## Menu
All views for users can be switched using menu button in top right corner of window.

<hr />

# Implementation
Implementation of an application consists of four main parts:
- loading fragments and switching between them.
- registration and logging users at Firebase Authentication service.
- loading and storing users' data at Cloud Firestore.
- providing a protocol of rating operation between two users.

![alt text](https://github.com/wojtekrafalo/LoveFinderz/blob/master/docs/img/nav_graph.png)


## Application
Project uses standard android Fragments and layout written in `xml` format. Application is developed using `kotlin` language. A graph of transitions between views is presented in Figure above.

An implementation of project was divided into two parts: back end and front end. The former consists of two main classes: `FirebaseAuthenticationManager` and `FirebaseDatabaseManager`. The latter is build using MVP pattern. It consist of:
* Model objects used to store information loaded from server.
* View classes used to present data (passed by presenter objects) at fragments written in `xml` files.
* Presenter classes, created for each view. Every class possesses a reference to database and authentication intercessory classes. Presenter classes' responsibility is to generate data and pass it to View classes.

![alt text](https://github.com/wojtekrafalo/LoveFinderz/blob/master/docs/img/users_auth.png)


## Firebase Authentication
Functionality of authentication is based on class `FirebaseAuthenticationManager`, what holds an instance of `FirebaseAuth` class and makes operations like `login`, `register`, `currentUser`, `logOut` with data passed be Presenter objects. Users stored in Firebase Authentication are presented in Firebase console view, as show in figure above ([See more](https://firebase.google.com/docs/auth)).

## Cloud Firestore
Data stored in server in consisted of two main tables represented by `JSON` format: `user` and `relation`.

Former consists of following fields:
* *id* - identifier of user.
* *username* - name of user provided while registration.
* *email* - address provided while registration.
* *dateOfBirth* - data tuple with fields: *day*, *month* and *year*.
* *relatedUsers* - array of identifiers of users, who current user has already rated.

Table *relation* in other way consists of following fields:
* *id* - generated identifier of data tuple.
* *users* - array of users, who rated each by function *like*.

![alt text](https://github.com/wojtekrafalo/LoveFinderz/blob/master/docs/img/users_db.png)


Storing and loading data from server database is implemented in `FirebaseDatabaseManager` class and provides REST operations. ([See more](https://firebase.google.com/docs/firestore)).


<hr />
# Protocol

## Introduction
While rating other user an application has to use protocol which main assumptions are:
* make rating other user independent of them,
* not to store and not to send personal choices to database in plaintext (excluding information about matching, which is public),
* provide full functionality even if one of the parties is currently offline. 

Described requirements are meet by using Yao’s Garbled Circuit method ([See more](https://homepages.cwi.nl/~schaffne/courses/crypto/2014/presentations/Kostis_Yao.pdf)) originally developed to provide possibility of counting outputs of functions together with other parties in such manner so nobody knows anything about inputs provided by parties but parties whose provided those.

Yao’s Garbled Circuit requires using oblivious transfer ([See more](https://crypto.stanford.edu/cs355/18sp/lec6.pdf)) - method of sending one of two massages *m<sub>0</sub>* and *m<sub>1</sub>* between party *A* and *B* so that party *A* do not know which of the two messages was chosen by *B* and *B* knows only one of the two messages.

We decided to use in our implementation Diffie-Hellman key exchange transfer ([See this](https://riliu.math.ncsu.edu/437/notes2se4.html) or [this](https://www.youtube.com/watch?v=pIi-YTBBolU)) because of its simple yet secure nature.

We also decided to use server between communicating parties to meet requirement of maintaining full app functionality even if one of the parties is currently offline which is crucial for proper user experience. 


### Specification
Algorithm of protocol is described below:

*Let:* <br/>
* *H: {0,1}<sup>\*</sup> -> {0,1}<sup>256</sup>* be a hashing function,
* *E: ({0,1}<sup>\*</sup>, {0,1}<sup>\*</sup>) -> {0,1}<sup>\*</sup>* be an encryption function,
* *D: ({0,1}<sup>\*</sup>, {0,1}<sup>\*</sup>) -> {0,1}<sup>\*</sup>* be an decryption function.
* *i \& j* be a concatenation of two words *i* and *j*.
*Then:*
* On the Alice side:
    * Alice generates four random keys *k<sub>A</sub><sup>0</sup>*, *k<sub>A</sub><sup>1</sup>*, *k<sub>B</sub><sup>0</sup>*, *k<sub>B</sub><sup>1</sup>*, random number *p*, big random prime number *n*, random number *g < n* where *GCD (n, g) = 1*.
    * Alice then encrypts four possible outputs of matching  with generated keys: <br/>
        *f<sub>1</sub> = E(k<sub>A</sub> <sup>0</sup>, E(k<sub>B</sub> <sup>0</sup>, 0))* <br/>
        *f<sub>2</sub> = E(k<sub>A</sub> <sup>1</sup>, E(k<sub>B</sub> <sup>0</sup>, 0))* <br/>
        *f<sub>3</sub> = E(k<sub>A</sub> <sup>0</sup>, E(k<sub>B</sub> <sup>1</sup>, 0))* <br/>
        *f<sub>4</sub> = E(k<sub>A</sub> <sup>1</sup>, E(k<sub>B</sub> <sup>1</sup>, 1))* <br/>
    * Alice randomly shuffles list *F = [ f<sub>1</sub>, f<sub>2</sub>, f<sub>3</sub>, f<sub>4</sub> ]* so the order of values gives no information about what is encrypted inside.
    * Alice computes *x = g<sup>p</sup> mod n*.
    * Alice sends over shuffled *F*, values of *g, n, x* to server as well as one of the keys *k<sub>A</sub> <sup>a</sup> = k<sub>A</sub> <sup>0</sup> or k<sub>A</sub> <sup>1</sup>*. She chooses *k<sub>A</sub> <sup>0</sup>* when her input is *0* and *k<sub>A</sub> <sup>1</sup>* when her input is *1*.

* On the Bob side:
    * Bob gets *g, n, x* from server, generates random number *q* and sends back  *y = g <sup>q</sup> mod n* if his input is *0* and *y = x g <sup>q</sup> mod n* if his input is *1*.
* On the Alice side:
    * Alice gets *y* from server.
    * Alice computes two keys $k_0 = H(y^p \textit{ mod } n)$ and $k_1 = H((\frac{y}{x})^p \textit{ mod } n)$.
    * Alice encrypts two Bob`s keys concatenated with sequence of ten zeros using those keys and creates ciphertexts <br/>
         *C<sub>0</sub> = E(k<sub>0</sub>, 0000000000 \& k<sub>B</sub> <sup>0</sup>)* and <br/>
         *C<sub>1</sub> = E(k<sub>1</sub>, 0000000000 \& k<sub>B</sub> <sup>1</sup>)*
    * Alice sends *C<sub>0</sub>, C<sub>1</sub>* to server.
* On the Bob side:
    * Bob gets *F* and *k<sub>A</sub> <sup>a</sup>* previously send by Alice as well as *C<sub>0</sub>* and *C<sub>1</sub>*.
    * Bob computes his key *k<sub>b</sub> = H(x<sup>q</sup> mod n)*.
    * Bob tries to decrypt two ciphertexts and gets two new values *d<sub>0</sub> = D(k<sub>b</sub>, C<sub>0</sub>)* and *d<sub>1</sub> = D(k<sub>b</sub>, C<sub>1</sub>)*. 
    * Because his key can decrypt correctly only one of those ciphertexts with great probability only one of the two retrieved values will start from the string of ten zeros so he will know that this is correctly decrypted message.
    * He deletes leading zeros and gets his key *k<sub>B</sub> <sup>b</sup>*.
    * He uses those two keys $k_B^b$ and $k_A^a$ to decrypt all values in $F$.
    * With great probability one of them will happen to be equal $0$ and $1$ and that is the output of their matching.
    * He sends retrieved output to the server.

Sequence diagram below shows communication between Alice, Bob and server:

![alt text](https://github.com/wojtekrafalo/LoveFinderz/blob/master/docs/img/Protocol_seq_diag.png)






## TODO:
+ Adding multiple photos by camera or gallery in phone.
+ Adding date of birth and age of users.
+ Sharing locations.
+ Chat, communicating protocol.
