import sys
import google.generativeai as genai

if __name__ == "__main__":
    my_api_key = sys.argv[1]
    args = sys.argv[2:]

    genai.configure(api_key=my_api_key)
    model = genai.GenerativeModel("gemini-1.5-flash")
    response = model.generate_content(args)

    print("Working...")
    print(response.text)