# CSED332 Assignment 4

Gwon Minjae



## Problem 1

Consider the following program to find the maximum value in an array. Write a Hoare logic proof (decorated program) to prove the given Hoare triple.


$\textcolor{blue}{\{0 < N\}}$
<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
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

$\textcolor{blue}{\{m=max(A[0],A[1],...,A[N−1])\}}$



#### Proof.

$\textcolor{blue}{\{0 < N\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
int m = A[0];
int i = 1;
</pre>

$\textcolor{blue}{\{1 \leq i \leq N \and m = max(A[0])\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
while (i < N){
</pre>

$\qquad\textcolor{blue}{\{1 \leq i \leq N \and m = max(A[0]) \and i < N\} \Longrightarrow}$

$\qquad\textcolor{blue}{\{1 \leq i + 1 \leq N \and m=max(A[0])\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
    if (A[i] > m)
        m = A[i];
    else
        skip;
</pre>

$\qquad\textcolor{blue}{\{1 \leq i + 1 \leq N \and m=max(A[0], A[i])\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
    i = i + 1;
</pre>

$\qquad\textcolor{blue}{\{1 \leq i \leq N \and m=max(A[0], A[i])\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
}
</pre>

$\textcolor{blue}{\{1 \leq i < N \and m=max(A[0], A[i])\}\Longrightarrow}$

$\textcolor{blue}{\{m=max(A[0],A[1],...,A[N−1])\}}$





## Problem 2

Write a Hoare logic proof (decorated program) to show that the given Hoare triple holds and the program always terminates (hint: what is a ranking function?).

$\textcolor{blue}{\{x\geq 0 \and y > 0\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
int r = x;
int q = 0;
while (y <= r) {
    r = r - y;
    q = q + 1;
}
</pre>

$\textcolor{blue}{\{x= qy + r \and 0 \leq r < y\}}$



#### Proof.

$\textcolor{blue}{\{x\geq 0 \and y > 0\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
int r = x;
int q = 0;
</pre>
$\textcolor{blue}{\{x \geq 0 \and y> 0 \and x = qy + r\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
while (y <= r) {
</pre>
$\qquad\textcolor{blue}{\{x \geq 0 \and y> 0 \and x = qy + r \and y \leq r\}\Longrightarrow}$

$\qquad \textcolor{blue}{\{x \geq 0 \and y> 0 \and x = (q + 1) y + (r-y) \and y \leq r\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
    r = r - y;
</pre>
$\qquad \textcolor{blue}{\{x \geq 0 \and y> 0 \and x = (q + 1) y + r \and 0 \leq r\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
    q = q + 1;
</pre>

$\qquad \textcolor{blue}{\{x \geq 0 \and y> 0 \and x = qy + r \and 0 \leq r\}}$


<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
}
</pre>

$\textcolor{blue}{\{x \geq 0 \and y> 0 \and x = qy + r \and 0 \leq r \and r < y\}\Longrightarrow}$

$\textcolor{blue}{\{x= qy + r \and 0 \leq r < y\}}$





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

