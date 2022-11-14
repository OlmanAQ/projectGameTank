% dijkstra(Vertex0, Ss) is true if Ss is a list of structures s(Vertex, Dist,
%   Path) containing the shortest Path from Vertex0 to Vertex, the distance of
%   the path being Dist.  The graph is defined by e/3.
% e.g. dijkstra(penzance, Ss)
dijkstra(Vertex, Ss):-
  create(Vertex, [Vertex], Ds),
  dijkstra_1(Ds, [s(Vertex,0,[])], Ss).

dijkstra_1([], Ss, Ss).  
dijkstra_1([D|Ds], Ss0, Ss):-
  best(Ds, D, S),
  delet([D|Ds], [S], Ds1),
  S=s(Vertex,Distance,Path),
  reverse([Vertex|Path], Path1),
  merg(Ss0, [s(Vertex,Distance,Path1)], Ss1),
  create(Vertex, [Vertex|Path], Ds2),
  delet(Ds2, Ss1, Ds3),
  incr(Ds3, Distance, Ds4),
  merg(Ds1, Ds4, Ds5),
  dijkstra_1(Ds5, Ss1, Ss).

% path(Vertex0, Vertex, Path, Dist) is true if Path is the shortest path from
%   Vertex0 to Vertex, and the length of the path is Dist. The graph is defined
%   by e/3.
% e.g. path(penzance, london, Path, Dist)
path(Vertex0, Vertex, Path, Dist):-
  dijkstra(Vertex0, Ss),
  member(s(Vertex,Dist,Path), Ss), !.

% create(Start, Path, Edges) is true if Edges is a list of structures s(Vertex,
%   Distance, Path) containing, for each Vertex accessible from Start, the
%   Distance from the Vertex and the specified Path.  The list is sorted by the
%   name of the Vertex.
create(Start, Path, Edges):-
  setof(s(Vertex,Edge,Path), e(Start,Vertex,Edge), Edges), !.
create(_, _, []).

% best(Edges, Edge0, Edge) is true if Edge is the element of Edges, a list of
%   structures s(Vertex, Distance, Path), having the smallest Distance.  Edge0
%   constitutes an upper bound.
best([], Best, Best).
best([Edge|Edges], Best0, Best):-
  shorter(Edge, Best0), !,
  best(Edges, Edge, Best).
best([_|Edges], Best0, Best):-
  best(Edges, Best0, Best).

shorter(s(_,X,_), s(_,Y,_)):-X < Y.

% delete(Xs, Ys, Zs) is true if Xs, Ys and Zs are lists of structures s(Vertex,
%   Distance, Path) ordered by Vertex, and Zs is the result of deleting from Xs
%   those elements having the same Vertex as elements in Ys.
delet([], _, []).
delet([X|Xs], [], [X|Xs]):-!.
delet([X|Xs], [Y|Ys], Ds):-
  eq(X, Y), !,
  delet(Xs, Ys, Ds).
delet([X|Xs], [Y|Ys], [X|Ds]):-
  lt(X, Y), !, delet(Xs, [Y|Ys], Ds).
delet([X|Xs], [_|Ys], Ds):-
  delet([X|Xs], Ys, Ds).

% merge(Xs, Ys, Zs) is true if Zs is the result of merging Xs and Ys, where Xs,
%   Ys and Zs are lists of structures s(Vertex, Distance, Path), and are
%   ordered by Vertex.  If an element in Xs has the same Vertex as an element
%   in Ys, the element with the shorter Distance will be in Zs.
merg([], Ys, Ys).
merg([X|Xs], [], [X|Xs]):-!.
merg([X|Xs], [Y|Ys], [X|Zs]):-
  eq(X, Y), shorter(X, Y), !,
  merg(Xs, Ys, Zs).
merg([X|Xs], [Y|Ys], [Y|Zs]):-
  eq(X, Y), !,
  merg(Xs, Ys, Zs).
merg([X|Xs], [Y|Ys], [X|Zs]):-
  lt(X, Y), !,
  merg(Xs, [Y|Ys], Zs).
merg([X|Xs], [Y|Ys], [Y|Zs]):-
  merg([X|Xs], Ys, Zs).

eq(s(X,_,_), s(X,_,_)).

lt(s(X,_,_), s(Y,_,_)):-X @< Y.

% incr(Xs, Incr, Ys) is true if Xs and Ys are lists of structures s(Vertex,
%   Distance, Path), the only difference being that the value of Distance in Ys
%   is Incr more than that in Xs.
incr([], _, []).
incr([s(V,D1,P)|Xs], Incr, [s(V,D2,P)|Ys]):-
  D2 is D1 + Incr,
  incr(Xs, Incr, Ys).

% member(X, Ys) is true if the element X is contained in the list Ys.
%member(X, [X|_]).
%member(X, [_|Ys]):-member(X, Ys).

% reverse(Xs, Ys) is true if Ys is the result of reversing the order of the
%   elements in the list Xs.
%reverse(Xs, Ys):-reverse_1(Xs, [], Ys).

%reverse_1([], As, As).
%reverse_1([X|Xs], As, Ys):-reverse_1(Xs, [X|As], Ys).

e(X, Y, Z):-dist(X, Y, Z).
e(X, Y, Z):-dist(Y, X, Z).

/* A subset of the data from EXAMPLES\SALESMAN.PL in LPA Win-Prolog */
dist(x00y00,   x01y00 ,   1).
dist(x01y00,   x02y00 ,   1).
dist(x02y00,   x03y00 ,   1).
dist(x03y00,   x04y00 ,   1).
dist(x04y00,   x05y00 ,   1).
dist(x05y00,   x06y00 ,   1).
dist(x06y00,   x07y00 ,   1).
dist(x07y00,   x08y00 ,   1).
dist(x08y00,   x09y00 ,   1).
dist(x09y00,   x10y00 ,   1).
dist(x10y00,   x11y00 ,   1).
dist(x11y00,   x12y00 ,   1).
dist(x12y00,   x13y00 ,   1).
dist(x13y00,   x14y00 ,   1).
dist(x14y00,   x15y00 ,   1).
dist(x15y00,   x16y00 ,   1).
dist(x16y00,   x17y00 ,   1).
dist(x17y00,   x18y00 ,   1).
dist(x18y00,   x19y00 ,   1).
dist(x19y00,   x20y00 ,   1).
dist(x20y00,   x21y00 ,   1).
dist(x21y00,   x22y00 ,   1).
dist(x22y00,   x23y00 ,   1).
dist(x23y00,   x24y00 ,   1).
dist(x24y00,   x25y00 ,   1).
dist(x25y00,   x26y00 ,   1).
dist(x26y00,   x27y00 ,   1).
dist(x27y00,   x28y00 ,   1).
dist(x28y00,   x29y00 ,   1).
dist(x29y00,   x30y00 ,   1).
dist(x30y00,   x31y00 ,   1).

dist(x00y00,   x00y01 ,   1).
dist(x02y00,   x02y01 ,   1).
dist(x03y00,   x03y01 ,   1).
dist(x04y00,   x04y01 ,   1).
dist(x05y00,   x05y01 ,   1).
dist(x06y00,   x06y01 ,   1).
dist(x08y00,   x08y01 ,   1).
dist(x10y00,   x10y01 ,   1).
dist(x12y00,   x12y01 ,   1).
dist(x13y00,   x13y01 ,   1).
dist(x14y00,   x14y01 ,   1).
dist(x15y00,   x15y01 ,   1).
dist(x16y00,   x16y01 ,   1).
dist(x17y00,   x17y01 ,   1).
dist(x18y00,   x18y01 ,   1).
dist(x19y00,   x19y01 ,   1).
dist(x20y00,   x20y01 ,   1).
dist(x21y00,   x21y01 ,   1).
dist(x22y00,   x22y01 ,   1).
dist(x23y00,   x23y01 ,   1).
dist(x24y00,   x24y01 ,   1).
dist(x25y00,   x25y01 ,   1).
dist(x26y00,   x26y01 ,   1).
dist(x27y00,   x27y01 ,   1).
dist(x28y00,   x28y01 ,   1).
dist(x29y00,   x29y01 ,   1).
dist(x30y00,   x30y01 ,   1).
dist(x31y00,   x31y01 ,   1).


dist(x02y01,   x03y01 ,   1).
dist(x03y01,   x04y01 ,   1).
dist(x04y01,   x05y01 ,   1).
dist(x05y01,   x06y01 ,   1).
dist(x06y01,   x07y01 ,   1).
dist(x07y01,   x08y01 ,   1).
dist(x08y01,   x09y01 ,   1).
dist(x09y01,   x10y01 ,   1).
dist(x10y01,   x11y01 ,   1).
dist(x11y01,   x12y01 ,   1).
dist(x12y01,   x13y01 ,   1).
dist(x13y01,   x14y01 ,   1).
dist(x14y01,   x15y01 ,   1).
dist(x15y01,   x16y01 ,   1).
dist(x16y01,   x17y01 ,   1).
dist(x17y01,   x18y01 ,   1).
dist(x18y01,   x19y01 ,   1).
dist(x19y01,   x20y01 ,   1).
dist(x20y01,   x21y01 ,   1).
dist(x21y01,   x22y01 ,   1).
dist(x22y01,   x23y01 ,   1).
dist(x23y01,   x24y01 ,   1).
dist(x24y01,   x25y01 ,   1).
dist(x25y01,   x26y01 ,   1).
dist(x26y01,   x27y01 ,   1).
dist(x27y01,   x28y01 ,   1).
dist(x28y01,   x29y01 ,   1).
dist(x29y01,   x30y01 ,   1).
dist(x30y01,   x31y01 ,   1).


dist(x00y01,   x00y02,    1).
dist(x02y01,   x02y02 ,   1).
dist(x04y01,   x04y02 ,   1).
dist(x05y01,   x05y02 ,   1).
dist(x08y01,   x08y02 ,   1).
dist(x10y01,   x10y02 ,   1).
dist(x12y01,   x12y02 ,   1).
dist(x13y01,   x13y02 ,   1).
dist(x14y01,   x14y02 ,   1).
dist(x15y01,   x15y02 ,   1).
dist(x16y01,   x16y02 ,   1).
dist(x17y01,   x17y02 ,   1).
dist(x18y01,   x18y02 ,   1).
dist(x19y01,   x19y02 ,   1).
dist(x20y01,   x20y02 ,   1).
dist(x21y01,   x21y02 ,   1).
dist(x22y01,   x22y02 ,   1).
dist(x23y01,   x23y02 ,   1).
dist(x24y01,   x24y02 ,   1).
dist(x25y01,   x25y02 ,   1).
dist(x26y01,   x26y02 ,   1).
dist(x27y01,   x27y02 ,   1).
dist(x28y01,   x28y02 ,   1).
dist(x29y01,   x29y02 ,   1).
dist(x30y01,   x30y02 ,   1).
dist(x31y01,   x31y02 ,   1).


dist(x04y02,   x05y02,    1).
dist(x05y02,   x06y02,    1).
dist(x06y02,   x07y02,    1).
dist(x07y02,   x08y02,    1).
dist(x08y02,   x09y02,    1).
dist(x09y02,   x10y02,    1).
dist(x10y02,   x11y02,    1).
dist(x11y02,   x12y02,    1).
dist(x12y02,   x13y02,    1).
dist(x13y02,   x14y02,    1).
dist(x14y02,   x15y02,    1).
dist(x15y02,   x16y02,    1).
dist(x16y02,   x17y02,    1).
dist(x17y02,   x18y02,    1).
dist(x18y02,   x19y02,    1).
dist(x19y02,   x20y02,    1).
dist(x20y02,   x21y02,    1).
dist(x21y02,   x22y02,    1).
dist(x22y02,   x23y02,    1).
dist(x23y02,   x24y02,    1).
dist(x24y02,   x25y02,    1).
dist(x25y02,   x26y02,    1).
dist(x26y02,   x27y02,    1).
dist(x27y02,   x28y02,    1).
dist(x28y02,   x29y02,    1).
dist(x29y02,   x30y02,    1).
dist(x30y02,   x31y02,    1).



dist(x00y02,   x00y03,    1).
dist(x02y02,   x02y03,    1).
dist(x04y02,   x04y03,    1).
dist(x05y02,   x05y03,    1).
dist(x08y02,   x08y03,    1).
dist(x10y02,   x10y03,    1).
dist(x12y02,   x12y03,    1).
dist(x13y02,   x13y03,    1).
dist(x14y02,   x14y03,    1).
dist(x15y02,   x15y03,    1).
dist(x16y02,   x16y03,    1).
dist(x17y02,   x17y03,    1).
dist(x18y02,   x18y03,    1).
dist(x19y02,   x19y03,    1).
dist(x20y02,   x20y03,    1).
dist(x21y02,   x21y03,    1).
dist(x22y02,   x22y03,    1).
dist(x23y02,   x23y03,    1).
dist(x24y02,   x24y03,    1).
dist(x25y02,   x25y03,    1).
dist(x26y02,   x26y03,    1).
dist(x27y02,   x27y03,    1).
dist(x28y02,   x28y03,    1).
dist(x29y02,   x29y03,    1).
dist(x30y02,   x30y03,    1).
dist(x31y02,   x31y03,    1).

dist(x00y03,   x01y03,    1).
dist(x01y03,   x02y03,    1).
dist(x04y03,   x05y03,    1).
dist(x07y03,   x08y03,    1).
dist(x08y03,   x09y03,    1).
dist(x09y03,   x10y03,    1).
dist(x10y03,   x11y03,    1).
dist(x11y03,   x12y03,    1).
dist(x12y03,   x13y03,    1).
dist(x13y03,   x14y03,    1).
dist(x14y03,   x15y03,    1).
dist(x15y03,   x16y03,    1).
dist(x16y03,   x17y03,    1).
dist(x17y03,   x18y03,    1).
dist(x18y03,   x19y03,    1).
dist(x19y03,   x20y03,    1).
dist(x20y03,   x21y03,    1).
dist(x21y03,   x22y03,    1).
dist(x22y03,   x23y03,    1).
dist(x23y03,   x24y03,    1).
dist(x24y03,   x25y03,    1).
dist(x25y03,   x26y03,    1).
dist(x26y03,   x27y03,    1).
dist(x27y03,   x28y03,    1).
dist(x28y03,   x29y03,    1).
dist(x29y03,   x30y03,    1).
dist(x30y03,   x31y03,    1).


dist(x00y03,   x00y04,    1).
dist(x02y03,   x02y04,    1).
dist(x04y03,   x04y04,    1).
dist(x05y03,   x05y04,    1).
dist(x07y03,   x07y04,    1).
dist(x08y03,   x08y04,    1).
dist(x10y03,   x10y04,    1).
dist(x12y03,   x12y04,    1).
dist(x13y03,   x13y04,    1).
dist(x14y03,   x14y04,    1).
dist(x15y03,   x15y04,    1).
dist(x16y03,   x16y04,    1).
dist(x17y03,   x17y04,    1).
dist(x18y03,   x18y04,    1).
dist(x19y03,   x19y04,    1).
dist(x20y03,   x20y04,    1).
dist(x21y03,   x21y04,    1).
dist(x22y03,   x22y04,    1).
dist(x23y03,   x23y04,    1).
dist(x24y03,   x24y04,    1).
dist(x25y03,   x25y04,    1).
dist(x26y03,   x26y04,    1).
dist(x27y03,   x27y04,    1).
dist(x28y03,   x28y04,    1).
dist(x29y03,   x29y04,    1).
dist(x30y03,   x30y04,    1).
dist(x31y03,   x31y04,    1).





dist(x04y04,   x05y04,    1).
dist(x05y04,   x06y04,    1).
dist(x06y04,   x07y04,    1).
dist(x07y04,   x08y04,    1).
dist(x08y04,   x09y04,    1).
dist(x09y04,   x10y04,    1).
dist(x10y04,   x11y04,    1).
dist(x11y04,   x12y04,    1).
dist(x12y04,   x13y04,    1).
dist(x13y04,   x14y04,    1).
dist(x14y04,   x15y04,    1).
dist(x15y04,   x16y04,    1).
dist(x16y04,   x17y04,    1).
dist(x17y04,   x18y04,    1).
dist(x18y04,   x19y04,    1).
dist(x19y04,   x20y04,    1).
dist(x20y04,   x21y04,    1).
dist(x21y04,   x22y04,    1).
dist(x22y04,   x23y04,    1).
dist(x23y04,   x24y04,    1).
dist(x24y04,   x25y04,    1).
dist(x25y04,   x26y04,    1).
dist(x26y04,   x27y04,    1).
dist(x27y04,   x28y04,    1).
dist(x28y04,   x29y04,    1).
dist(x29y04,   x30y04,    1).
dist(x30y04,   x31y04,    1).

dist(x00y04,   x00y05,    1).
dist(x02y04,   x02y05,    1).
dist(x04y04,   x04y05,    1).
dist(x08y04,   x08y05,    1).
dist(x10y04,   x10y05,    1).
dist(x12y04,   x12y05,    1).
dist(x13y04,   x13y05,    1).
dist(x14y04,   x14y05,    1).
dist(x15y04,   x15y05,    1).
dist(x16y04,   x16y05,    1).
dist(x17y04,   x17y05,    1).
dist(x18y04,   x18y05,    1).
dist(x19y04,   x19y05,    1).
dist(x20y04,   x20y05,    1).
dist(x21y04,   x21y05,    1).
dist(x22y04,   x22y05,    1).
dist(x23y04,   x23y05,    1).
dist(x24y04,   x24y05,    1).
dist(x25y04,   x25y05,    1).
dist(x26y04,   x26y05,    1).
dist(x27y04,   x27y05,    1).
dist(x28y04,   x28y05,    1).
dist(x29y04,   x29y05,    1).
dist(x30y04,   x30y05,    1).
dist(x31y04,   x31y05,    1).


dist(x00y05,   x01y05,    1).
dist(x01y05,   x02y05,    1).
dist(x02y05,   x03y05,    1).
dist(x03y05,   x04y05,    1).
dist(x08y05,   x09y05,    1).
dist(x09y05,   x10y05,    1).
dist(x10y05,   x11y05,    1).
dist(x11y05,   x12y05,    1).
dist(x12y05,   x13y05,    1).
dist(x13y05,   x14y05,    1).
dist(x14y05,   x15y05,    1).
dist(x15y05,   x16y05,    1).
dist(x16y05,   x17y05,    1).
dist(x17y05,   x18y05,    1).
dist(x18y05,   x19y05,    1).
dist(x19y05,   x20y05,    1).
dist(x20y05,   x21y05,    1).
dist(x21y05,   x22y05,    1).
dist(x22y05,   x23y05,    1).
dist(x23y05,   x24y05,    1).
dist(x24y05,   x25y05,    1).
dist(x25y05,   x26y05,    1).
dist(x26y05,   x27y05,    1).
dist(x27y05,   x28y05,    1).
dist(x28y05,   x29y05,    1).
dist(x29y05,   x30y05,    1).
dist(x30y05,   x31y05,    1).


dist(x00y05,   x00y06,    1).
dist(x04y05,   x04y06,    1).
dist(x08y05,   x08y06,    1).
dist(x09y05,   x09y06,    1).
dist(x10y05,   x10y06,    1).
dist(x12y05,   x12y06,    1).
dist(x13y05,   x13y06,    1).
dist(x14y05,   x14y06,    1).
dist(x15y05,   x15y06,    1).
dist(x16y05,   x16y06,    1).
dist(x17y05,   x17y06,    1).
dist(x18y05,   x18y06,    1).
dist(x19y05,   x19y06,    1).
dist(x20y05,   x20y06,    1).
dist(x21y05,   x21y06,    1).
dist(x22y05,   x22y06,    1).
dist(x23y05,   x23y06,    1).
dist(x24y05,   x24y06,    1).
dist(x25y05,   x25y06,    1).
dist(x26y05,   x26y06,    1).
dist(x27y05,   x27y06,    1).
dist(x28y05,   x28y06,    1).
dist(x29y05,   x29y06,    1).
dist(x30y05,   x30y06,    1).
dist(x31y05,   x31y06,    1).


dist(x04y06,   x05y06,    1).
dist(x05y06,   x06y06,    1).
dist(x06y06,   x07y06,    1).
dist(x07y06,   x08y06,    1).
dist(x08y06,   x09y06,    1).
dist(x09y06,   x10y06,    1).
dist(x10y06,   x11y06,    1).
dist(x11y06,   x12y06,    1).
dist(x12y06,   x13y06,    1).
dist(x13y06,   x14y06,    1).
dist(x14y06,   x15y06,    1).
dist(x15y06,   x16y06,    1).
dist(x16y06,   x17y06,    1).
dist(x17y06,   x18y06,    1).
dist(x18y06,   x19y06,    1).
dist(x19y06,   x20y06,    1).
dist(x20y06,   x21y06,    1).
dist(x21y06,   x22y06,    1).
dist(x22y06,   x23y06,    1).
dist(x23y06,   x24y06,    1).
dist(x24y06,   x25y06,    1).
dist(x25y06,   x26y06,    1).
dist(x26y06,   x27y06,    1).
dist(x27y06,   x28y06,    1).
dist(x28y06,   x29y06,    1).
dist(x29y06,   x30y06,    1).
dist(x30y06,   x31y06,    1).


dist(x00y06,   x00y07,    1).
dist(x04y06,   x04y07,    1).
dist(x06y06,   x06y07,    1).
dist(x07y06,   x07y07,    1).
dist(x08y06,   x08y07,    1).
dist(x10y06,   x10y07,    1).
dist(x12y06,   x12y07,    1).
dist(x13y06,   x13y07,    1).
dist(x14y06,   x14y07,    1).
dist(x15y06,   x15y07,    1).
dist(x16y06,   x16y07,    1).
dist(x17y06,   x17y07,    1).
dist(x18y06,   x18y07,    1).
dist(x19y06,   x19y07,    1).
dist(x20y06,   x20y07,    1).
dist(x21y06,   x21y07,    1).
dist(x22y06,   x22y07,    1).
dist(x23y06,   x23y07,    1).
dist(x24y06,   x24y07,    1).
dist(x25y06,   x25y07,    1).
dist(x26y06,   x26y07,    1).
dist(x27y06,   x27y07,    1).
dist(x28y06,   x28y07,    1).
dist(x29y06,   x29y07,    1).
dist(x30y06,   x30y07,    1).
dist(x31y06,   x31y07,    1).

dist(x00y07,   x01y07,    1).
dist(x01y07,   x02y07,    1).
dist(x02y07,   x03y07,    1).
dist(x03y07,   x04y07,    1).
dist(x06y07,   x07y07,    1).
dist(x07y07,   x08y07,    1).
dist(x08y07,   x09y07,    1).
dist(x09y07,   x10y07,    1).
dist(x10y07,   x11y07,    1).
dist(x11y07,   x12y07,    1).
dist(x12y07,   x13y07,    1).
dist(x13y07,   x14y07,    1).
dist(x14y07,   x15y07,    1).
dist(x15y07,   x16y07,    1).
dist(x16y07,   x17y07,    1).
dist(x17y07,   x18y07,    1).
dist(x18y07,   x19y07,    1).
dist(x19y07,   x20y07,    1).
dist(x20y07,   x21y07,    1).
dist(x21y07,   x22y07,    1).
dist(x22y07,   x23y07,    1).
dist(x23y07,   x24y07,    1).
dist(x24y07,   x25y07,    1).
dist(x25y07,   x26y07,    1).
dist(x26y07,   x27y07,    1).


dist(x00y07,   x00y08,    1).
dist(x02y07,   x02y08,    1).
dist(x03y07,   x03y08,    1).
dist(x04y07,   x04y08,    1).
dist(x06y07,   x06y08,    1).
dist(x07y07,   x07y08,    1).
dist(x08y07,   x08y08,    1).
dist(x10y07,   x10y08,    1).
dist(x12y07,   x12y08,    1).

dist(x02y08,   x03y08,    1).
dist(x03y08,   x04y08,    1).
dist(x04y08,   x05y08,    1).
dist(x05y08,   x06y08,    1).
dist(x06y08,   x07y08,    1).
dist(x07y08,   x08y08,    1).

dist(x00y08,   x00y09,    1).
dist(x02y08,   x02y09,    1).
dist(x04y08,   x04y09,    1).
dist(x08y08,   x08y09,    1).
dist(x10y08,   x10y09,    1).
dist(x12y08,   x12y09,    1).

dist(x08y09,   x09y09,    1).
dist(x09y09,   x10y09,    1).

dist(x00y09,   x00y10,    1).
dist(x02y09,   x02y10,    1).
dist(x04y09,   x04y10,    1).
dist(x08y09,   x08y10,    1).
dist(x10y09,   x10y10,    1).
dist(x12y09,   x12y10,    1).

dist(x00y10,   x01y10,    1).
dist(x01y10,   x02y10,    1).
dist(x04y10,   x05y10,    1).
dist(x05y10,   x06y10,    1).
dist(x06y10,   x07y10,    1).
dist(x07y10,   x08y10,    1).

dist(x00y10,   x00y11,    1).
dist(x02y10,   x02y11,    1).
dist(x04y10,   x04y11,    1).
dist(x06y10,   x06y11,    1).
dist(x08y10,   x08y11,    1).
dist(x10y10,   x10y11,    1).
dist(x12y10,   x12y11,    1).


dist(x02y11,   x03y11,    1).
dist(x03y11,   x04y11,    1).
dist(x10y11,   x11y11,    1).
dist(x11y11,   x12y11,    1).
