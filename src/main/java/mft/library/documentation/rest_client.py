import pandas as pd
import streamlit as st
import requests

resp = requests.get("http://localhost/persons")
df = pd.DataFrame(resp.json())
st.write(df)
