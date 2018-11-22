#lang racket

(define spreadsheet (list (list 798 1976 1866 1862 559 1797 1129 747 85 1108 104 2000 248 131 87 95)
                          (list 201 419 336 65 208 57 74 433 68 360 390 412 355 209 330 135)
                          (list 967 84 492 1425 1502 1324 1268 1113 1259 81 310 1360 773 69 68 290)
                          (list 169 264 107 298 38 149 56 126 276 45 305 403 89 179 394 172)
                          (list 3069 387 2914 2748 1294 1143 3099 152 2867 3082 113 145 2827 2545 134 469)
                          (list 3885 1098 2638 5806 4655 4787 186 4024 2286 5585 5590 215 5336 2738 218 266)
                          (list 661 789 393 159 172 355 820 891 196 831 345 784 65 971 396 234)
                          (list 4095 191 4333 161 3184 193 4830 4153 2070 3759 1207 3222 185 176 2914 4152)
                          (list 131 298 279 304 118 135 300 74 269 96 366 341 139 159 17 149)
                          (list 1155 5131 373 136 103 5168 3424 5126 122 5046 4315 126 236 4668 4595 4959)
                          (list 664 635 588 673 354 656 70 86 211 139 95 40 84 413 618 31)
                          (list 2163 127 957 2500 2370 2344 2224 1432 125 1984 2392 379 2292 98 456 154)
                          (list 271 4026 2960 6444 2896 228 819 676 6612 6987 265 2231 2565 6603 207 6236)
                          (list 91 683 736 1998 1960 1727 84 1992 1072 1588 1768 74 58 1956 1627 893)
                          (list 3591 1843 3448 1775 3564 2632 1002 3065 77 3579 78 99 1668 98 2963 3553)
                          (list 2155 225 2856 3061 105 204 1269 171 2505 2852 977 1377 181 1856 2952 2262)))

(define (checksum-sheet row-calc sheet-calc sheet)
  (apply sheet-calc (map row-calc sheet)))

(define (part-1)
  (let ([row-difference (lambda (row) (- (apply max row) (apply min row)))])
    (checksum-sheet row-difference + spreadsheet)))

(define (part-2)
  (let* ([divide-pair (lambda (p) (apply / (sort p >)))]
         [row-combinations (lambda (row) (combinations row 2))]
         [row-pairs (lambda (row) (map divide-pair (row-combinations row)))]
         [row-value (lambda (row) (first (filter integer? (row-pairs row))))])
    (checksum-sheet row-value + spreadsheet)))


(part-1)
; 41919

(part-2)
; 303
