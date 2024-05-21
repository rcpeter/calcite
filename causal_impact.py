import sys
import numpy as np
from causalml.inference.meta import BaseXRegressor
from sklearn.linear_model import LinearRegression

def compute_cate(source, target):
    # Repalce the code here with source, target, treatment, outcome and model section to return cate post execution
    return cate_score

if __name__ == "__main__":
    source = sys.argv[1]
    target = sys.argv[2]
    cate_score = compute_cate(source, target)
    print(f"CATE Score: {cate_score}")