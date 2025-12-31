╔═══════════════════════════════════════╗
║          VOICE NOTES APP              ║
║         User Interface Layout         ║
╚═══════════════════════════════════════╝

┌─────────────────────────────────────┐
│                                     │
│         Voice Notes                 │  <- App Title (bold, 24sp)
│                                     │
├─────────────────────────────────────┤
│                                     │
│  ╔═══════════════════════════════╗  │
│  ║                               ║  │
│  ║  Your notes will appear       ║  │  <- Scrollable notes area
│  ║  here...                      ║  │     (initially shows placeholder)
│  ║                               ║  │
│  ║  [10:30:15] Pick up groceries ║  │  <- Example note with timestamp
│  ║                               ║  │
│  ║  [10:35:42] Call dentist     ║  │  <- Another example note
│  ║  tomorrow morning             ║  │
│  ║                               ║  │
│  ║                               ║  │
│  ╚═══════════════════════════════╝  │
│                                     │
├─────────────────────────────────────┤
│                                     │
│  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  │
│  ┃    🎤 Tap to Speak           ┃  │  <- Large button (easy to tap)
│  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  │
│                                     │
└─────────────────────────────────────┘

USER FLOW:
──────────

1. Launch App
   └─> Grant microphone permission (first time only)

2. Tap "🎤 Tap to Speak" button
   └─> Android speech recognition dialog appears

3. Speak your note
   └─> "Speak your note..." prompt shown
   └─> Voice indicator animates

4. Finish speaking
   └─> Recognition processes speech
   └─> Note appears with timestamp
   └─> Scroll automatically to show new note

5. Repeat as needed!

FEATURES IN ACTION:
──────────────────

✓ Large tap target - Easy to hit while driving
✓ Voice-first - No typing ever needed
✓ Auto-timestamp - Know when each note was created
✓ Auto-scroll - Latest note always visible
✓ Dark mode - Respects system theme
✓ Simple - One screen, one purpose

PERMISSION FLOW:
───────────────

First Launch:
1. App opens
2. User taps microphone button
3. Android shows permission dialog:
   "Allow Voice Notes to record audio?"
   [Deny] [Allow]
4. User taps [Allow]
5. Speech recognition starts immediately

Subsequent Launches:
1. App opens
2. User taps microphone button
3. Speech recognition starts immediately
   (no permission prompt - already granted)

SPEECH RECOGNITION DIALOG:
─────────────────────────

┌─────────────────────────┐
│  Speak your note...     │  <- Prompt text
│                         │
│         🎤              │  <- Animated microphone
│      ▂▄▆▇█▇▆▄▂          │  <- Voice level indicator
│                         │
│  "Pick up groceries"    │  <- Real-time transcription
│                         │
│      [Cancel]           │  <- Cancel button
└─────────────────────────┘

NOTES DISPLAY:
─────────────

Initial state:
┌──────────────────────┐
│ Your notes will      │
│ appear here...       │
└──────────────────────┘

After first note:
┌──────────────────────┐
│ [10:30:15] Pick up   │
│ groceries            │
└──────────────────────┘

After multiple notes:
┌──────────────────────┐
│ [10:30:15] Pick up   │
│ groceries            │
│                      │
│ [10:35:42] Call      │
│ dentist tomorrow     │
│ morning              │
│                      │
│ [11:02:09] Email     │
│ project update to    │
│ team                 │
└──────────────────────┘

KEY DESIGN DECISIONS:
────────────────────

• Single button: Reduces cognitive load
• Large text: Easy to read at a glance
• Timestamps: Provide context
• Scrollable: Handle many notes
• Material Design: Familiar Android look
• Theme-aware: Dark/light mode support
• No delete button: Minimizes interaction
• Notes are temporary: Session-based

TYPICAL USAGE WHILE DRIVING:
───────────────────────────

Scenario: Remember to buy groceries

1. Glance at phone at red light
2. Tap large microphone button
3. Speak: "Pick up groceries after work"
4. Done! Note saved with timestamp
5. Light turns green, continue driving

Total interaction time: ~5 seconds

Much safer than:
- Typing on keyboard
- Fighting with AI assistant
- Dealing with recognition failures
- Multiple app interactions

