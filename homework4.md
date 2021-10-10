# CSED332 Assignment 4

Gwon Minjae



## Problem 1

Consider the following program to find the maximum value in an array. Write a Hoare logic proof (decorated program) to prove the given Hoare triple.


$\{0 < N\}$
<pre style="font-family: monospace">
int m = A[0];
int i = 1;
while (i < N){
    if (A[i] > m)
        m = A[i];
    else
        skip;
    i = i + 1;
}
</pre>

$\{m=max(A[0],A[1],...,A[N−1])\}$



#### Proof.





## Problem 2

Write a Hoare logic proof (decorated program) to show that the given Hoare triple holds and the program always terminates (hint: what is a ranking function?).

$\{x\geq 0 \and y > 0\}$

<pre style="font-family: monospace">
int r = x;
int q = 0;
while (y <= r) {
    r = r - y;
    q = q + 1;
}
</pre>

$\{x= qy + r \and 0 \leq r < y\}$



#### Proof.





## Problem 3

Consider the following program for sorting an array. Write a Hoare logic proof to prove the given Hoare triple, where $sorted(a_1,a_2,...,a_k)$ means $a1 \leq a2 \leq ··· \leq ak$.

$\{0 \leq N\}$

<pre style="font-family: monospace">
int i = 1;
while (i < N) {
    int j = i;
    while (j > 0 && A[j-1] > A[j]) {
        int t = A[j-1];
        A[j-1] = A[j];
        A[j] = t;
        j = j - 1;
    }
    i = i + 1;
}
</pre>

$\{sorted (A[0], A[1], A[2], . . . , A[N − 1])\}$



#### Proof.

