import sys
import google.generativeai as genai

if __name__ == "__main__":
    my_api_key = sys.argv[1]
    args = sys.argv[2:]

    genai.configure(api_key=my_api_key)
    model = genai.GenerativeModel("gemini-1.5-flash")
    chat = model.start_chat(history=[])

    response = chat.generate_content(
        args,
        safety_settings={
            HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT : HarmBlockThreshold.BLOCK_NONE,
            HarmCategory.HARM_CATEGORY_HATE_SPEECH : HarmBlockThreshold.BLOCK_NONE,
            HarmCategory.HARM_CATEGORY_HARASSMENT : HarmBlockThreshold.BLOCK_NONE,
            HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT : HarmBlockThreshold.BLOCK_NONE
        }
    )

    print("Working...")
    print(response.text)