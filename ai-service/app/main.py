from fastapi import FastAPI
from pydantic import BaseModel
import os
from openai import OpenAI
import re

app = FastAPI(title="AI Task Service", version="1.0")

MODEL_RUNNER_URL = os.getenv("MODEL_RUNNER_URL")
MODEL_NAME = os.getenv("MODEL_NAME")

client = OpenAI(base_url=MODEL_RUNNER_URL, api_key="no-key-required")


class TaskRequest(BaseModel):
    task_text: str
    user_id: int
    deadline: str | None = None     # Just a filler for now, eg "2025-12-10 17:00"


class TaskResponse(BaseModel):
    evaluation: int  # 0–127


def evaluate_importance(text: str, deadline: str | None = None) -> int:
    prompt = (
        "You are an AI assistant that evaluates task importance.\n"
        "Rate the importance of tasks on a scale from 0 (completely trivial) "
        "to 127 (extremely critical, must be done immediately).\n"
        "Consider urgency, consequences, and human context. Some tasks may seem small "
        "but are still important.\n\n"
        "Here are some examples:\n"
        "- Task: Shower -> Importance: 10\n"
        "- Task: Eat breakfast -> Importance: 20\n"
        "- Task: Submit project report due today -> Importance: 120\n"
        "- Task: Pay electricity bill due in 2 hours -> Importance: 110\n\n"
    )

    if deadline:
        prompt += f"The task deadline is: {deadline}\n\n"

    prompt += f"Now rate this task:\nTask: {text}\nRespond with only a number between 0 and 127."

    print(f"[AI-SERVICE] Sending prompt to model:\n{prompt}")

    try:
        completion = client.chat.completions.create(
            model=MODEL_NAME,
            messages=[{"role": "user", "content": prompt}],
            max_tokens=20,
            temperature=0.5
        )
        reply = completion.choices[0].message.content.strip()
        print(f"[AI-SERVICE] Raw model response: {reply}")

        match = re.search(r'\d+', reply)
        if match:
            score = int(match.group())
            final_score = min(max(score, 0), 127)
            print(f"[AI-SERVICE] Parsed evaluation score: {final_score}")
            return final_score

    except Exception as e:
        print(f"[AI-SERVICE] Error calling model: {e}")

    print("[AI-SERVICE] No valid number found. Returning 0.")
    return 0



@app.post("/evaluate", response_model=TaskResponse)
def evaluate_task(request: TaskRequest):
    print(f"[AI-SERVICE] Received request: {request}")
    score = evaluate_importance(request.task_text, request.deadline)
    print(f"[AI-SERVICE] Final response score: {score}")
    return {"evaluation": score}
