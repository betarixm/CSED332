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

$\textcolor{blue}{\{i = 1 \and m = max(A[0], \cdots, A[i-1])\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
while (i < N){
</pre>

$\qquad\textcolor{blue}{\{1 \leq i < N \and m = max(A[0], \cdots, A[i-1])\} \Longrightarrow}$

$\qquad\textcolor{blue}{\{1 \leq i + 1 \leq N \and m = max(A[0], \cdots, A[i-1])\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
    if (A[i] > m)
        m = A[i];
    else
        skip;
</pre>

$\qquad\textcolor{blue}{\{1 \leq i + 1 \leq N \and m=max(A[0], \cdots, A[i])\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
    i = i + 1;
</pre>

$\qquad\textcolor{blue}{\{1 \leq i \leq N \and m=max(A[0], \cdots, A[i-1])\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
}
</pre>
$\textcolor{blue}{\{1 \leq i \leq N \and m=max(A[0], \cdots, A[i-1]) \and i \geq N\} \Longrightarrow}$

$\textcolor{blue}{\{i = N \and m=max(A[0], \cdots, A[i-1])\} \Longrightarrow}$

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

Consider the following program for sorting an array. Write a Hoare logic proof to prove the given Hoare triple, where $sorted(a_1,a_2,...,a_k)$ means $a_1 \leq a_2 \leq ··· \leq a_k$.

$\textcolor{blue}{\{0 \leq N\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
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

$\textcolor{blue}{\{sorted (A[0], A[1], A[2], . . . , A[N − 1])\}}$



#### Prerequisite.

$$
\begin{align*}
S(a, b) &= \begin{cases}
sorted(A[a], \cdots, A[b]), &\text{if } 0 \leq a \leq b < N \\
\{\}, &\text{otherwise.}
\end{cases} \\
L(a, b) &= \begin{cases}
\{A[a], \cdots A[b]\}, &\text{if } 0 \leq a \leq b < N \\
\{\}, &\text{otherwise.}
\end{cases} \\
R &= \text{(WLOG) Remaining part of A}
\end{align*}
$$



#### Proof.

$\textcolor{blue}{\{0 \leq N\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
int i = 1;
</pre>
$\textcolor{blue}{\{0 \leq N \and i \geq 1 \and A = sorted(A[0]) + \{A[1], \cdots, A[N-1]\}\} \Longrightarrow}$

$\textcolor{blue}{\{0 \leq N \and i \geq 1 \and A = sorted(A[0], \cdots , A[i-1]) + \{A[i], \cdots, A[N-1]\}\}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
while (i < N) {
</pre>
$\qquad\textcolor{blue}{\{0 \leq N \and i \geq 1 \and A = sorted(A[0], \cdots , A[i-1]) + \{A[i], \cdots, A[N-1]\} \and i < N\} \Longrightarrow}$

$\qquad\textcolor{blue}{\{0 \leq N \and 1 \leq i < N \and A = sorted(A[0], \cdots , A[i-1]) + \{A[i], \cdots, A[N-1]\}\} \Longrightarrow}$

$\qquad\textcolor{blue}{\{0 \leq N \and 1 \leq i + 1 \leq N \and A = sorted(A[0], \cdots , A[(i+1)-2]) + \{A[(i+1) - 1], \cdots, A[N-1]\}\} \Longrightarrow}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
    int j = i;
</pre>
$\qquad\textcolor{blue}{\{0 \leq N \and 1 \leq i + 1 \leq N \and A = sorted(A[0], \cdots , A[(i+1)-2]) + \{A[(i+1) - 1], \cdots, A[N-1]\} \and j \leq i\} \Longrightarrow}$

$\qquad\textcolor{blue}{\begin{align*}\{0 \leq N &\and 1 \leq i + 1 \leq N \and A = S(0, j - 2) + L(j - 1, j) + S(j + 1, i-1) + R\and j \leq i \} \end{align*}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
    while (j > 0 && A[j-1] > A[j]) {
</pre>
$\qquad\qquad\textcolor{blue}{\begin{align*}\{0 \leq N &\and 1 \leq i + 1 \leq N \and A = S(0, j - 2) + L(j - 1, j) + S(j + 1, i-1) + R\and j \leq i \\ &\and j > 0 \and A[j-1] > A[j] \} \Longrightarrow \end{align*}}$

$\qquad\qquad\textcolor{blue}{\begin{align*}\{0 \leq N &\and 1 \leq i + 1 \leq N \and A = S(0, j - 2) + L(j - 1, j) + S(j + 1, i-1) + R \\ &\and 0 \leq j - 1 \leq i \and A[j-1] > A[j] \} \end{align*}}$

<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
        int t = A[j-1];
        A[j-1] = A[j];
        A[j] = t;
</pre>
$\qquad\qquad\textcolor{blue}{\begin{align*}\{0 \leq N &\and 1 \leq i + 1 \leq N \and A = S(0, (j - 1) - 2) + L((j - 1) - 1, j - 1) + S((j - 1) + 1, i-1) + R \\ &\and 0 \leq j - 1 \leq i \and A[j-1] \leq A[j] \} \end{align*}}$
<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
        j = j - 1;
</pre>
$\qquad\qquad\textcolor{blue}{\begin{align*}\{0 \leq N &\and 1 \leq i + 1 \leq N \and A = S(0, j - 2) + L(j - 1, j) + S(j + 1, i-1) + R \\ &\and 0 \leq j \leq i \and A[j] \leq A[j + 1] \} \end{align*}}$
<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
    }
</pre>
$\qquad\textcolor{blue}{\begin{align*}\{0 \leq N &\and 1 \leq i + 1 \leq N \and A = S(0, j - 2) + L(j - 1, j) + S(j + 1, i-1) + R \\ &\and 0 \leq j \leq i \and A[j] \leq A[j + 1] \and (j \leq 0 \or A[j - 1] \leq A[j]) \} \Longrightarrow\end{align*}}$

$\qquad\textcolor{blue}{\begin{align*}\{0 \leq N &\and 1 \leq i + 1 \leq N \and A = sorted(A[0], \cdots, A[i]) + \{A[(i + 1)],\cdots, A[N-1]\} \} \Longrightarrow\end{align*}}$

$\qquad\textcolor{blue}{\{0 \leq N \and 1 \leq i + 1 \leq N \and A = sorted(A[0], \cdots , A[(i + 1) - 1]) + \{A[(i + 1)],\cdots, A[N-1]\}\}}$


<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
    i = i + 1;
</pre>

$\qquad\textcolor{blue}{\{0 \leq N \and 1 \leq i \leq N \and A = sorted(A[0], \cdots , A[i-1]) + \{A[i], A[i + 1], \cdots, A[N-1]\}\}}$
<pre style="font-family: monospace; background-color: transparent; padding: 0; margin: 0;">
}
</pre>

$\textcolor{blue}{\{0 \leq N \and 1 \leq i \leq N \and A = sorted(A[0], \cdots , A[i-1]) + \{A[i], A[i + 1], \cdots, A[N-1]\} \and i \geq N\} \Longrightarrow}$

$\textcolor{blue}{\{0 \leq N \and i = N \and A = sorted(A[0], \cdots , A[i-1]) + \{A[i], A[i + 1], \cdots, A[N-1]\}\} \Longrightarrow}$

$\textcolor{blue}{\{0 \leq N \and i = N \and A = sorted(A[0], \cdots , A[N-1])\} \Longrightarrow}$

$\textcolor{blue}{\{sorted (A[0], A[1], A[2], . . . , A[N − 1])\}}$


