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

dist(x00y00,   x00y01 ,   1).
dist(x02y00,   x02y01 ,   1).
dist(x03y00,   x03y01 ,   1).
dist(x04y00,   x04y01 ,   1).
dist(x05y00,   x05y01 ,   1).
dist(x06y00,   x06y01 ,   1).
dist(x08y00,   x08y01 ,   1).
dist(x10y00,   x10y01 ,   1).
dist(x12y00,   x12y01 ,   1).

dist(x02y01,   x03y01 ,   1).
dist(x03y01,   x04y01 ,   1).
dist(x04y01,   x05y01 ,   1).
dist(x05y01,   x06y01 ,   1).

dist(x00y01,   x00y02,    1).
dist(x02y01,   x02y02 ,   1).
dist(x04y01,   x04y02 ,   1).
dist(x05y01,   x05y02 ,   1).
dist(x08y01,   x08y02 ,   1).
dist(x10y01,   x10y02 ,   1).
dist(x12y01,   x12y02 ,   1).

dist(x04y02,   x05y02,    1).

dist(x00y02,   x00y03,    1).
dist(x02y02,   x02y03,    1).
dist(x04y02,   x04y03,    1).
dist(x05y02,   x05y03,    1).
dist(x08y02,   x08y03,    1).
dist(x10y02,   x10y03,    1).
dist(x12y02,   x12y03,    1).

dist(x00y03,   x01y03,    1).
dist(x01y03,   x02y03,    1).
dist(x04y03,   x05y03,    1).
dist(x07y03,   x08y03,    1).
dist(x08y03,   x09y03,    1).
dist(x09y03,   x10y03,    1).
dist(x10y03,   x11y03,    1).
dist(x11y03,   x12y03,    1).

dist(x00y03,   x00y04,    1).
dist(x02y03,   x02y04,    1).
dist(x04y03,   x04y04,    1).
dist(x05y03,   x05y04,    1).
dist(x07y03,   x07y04,    1).
dist(x08y03,   x08y04,    1).
dist(x10y03,   x10y04,    1).
dist(x12y03,   x12y04,    1).

dist(x04y04,   x05y04,    1).
dist(x05y04,   x06y04,    1).
dist(x06y04,   x07y04,    1).
dist(x07y04,   x08y04,    1).

dist(x00y04,   x00y05,    1).
dist(x02y04,   x02y05,    1).
dist(x04y04,   x04y05,    1).
dist(x08y04,   x08y05,    1).
dist(x10y04,   x10y05,    1).
dist(x12y04,   x12y05,    1).

dist(x00y05,   x01y05,    1).
dist(x01y05,   x02y05,    1).
dist(x02y05,   x03y05,    1).
dist(x03y05,   x04y05,    1).
dist(x08y05,   x09y05,    1).
dist(x09y05,   x10y05,    1).
dist(x10y05,   x11y05,    1).
dist(x11y05,   x12y05,    1).

dist(x00y05,   x00y06,    1).
dist(x04y05,   x04y06,    1).
dist(x08y05,   x08y06,    1).
dist(x09y05,   x09y06,    1).
dist(x10y05,   x10y06,    1).
dist(x12y05,   x12y06,    1).

dist(x04y06,   x05y06,    1).
dist(x05y06,   x06y06,    1).
dist(x06y06,   x07y06,    1).
dist(x07y06,   x08y06,    1).
dist(x08y06,   x09y06,    1).
dist(x09y06,   x10y06,    1).

dist(x00y06,   x00y07,    1).
dist(x04y06,   x04y07,    1).
dist(x06y06,   x06y07,    1).
dist(x07y06,   x07y07,    1).
dist(x08y06,   x08y07,    1).
dist(x10y06,   x10y07,    1).
dist(x12y06,   x12y07,    1).

dist(x00y07,   x01y07,    1).
dist(x01y07,   x02y07,    1).
dist(x02y07,   x03y07,    1).
dist(x03y07,   x04y07,    1).
dist(x06y07,   x07y07,    1).
dist(x07y07,   x08y07,    1).

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
