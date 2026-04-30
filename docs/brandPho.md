# Injury Assist — Brand Philosophy & UI Design Guide

## Brand identity

**Name:** Injury Assist
**Tagline:** "Know your injury. Own your recovery."
**Audience:** Amateur and semi-professional athletes aged 16–35.
**Tone:** Calm, clinical, trustworthy — never alarming, never casual.

The design must make a scared, injured person feel immediately safe and guided.
Every screen should feel like a quiet clinic, not a busy hospital.

---

## Core design values

| Value | What it means in practice |
|---|---|
| Clarity | One action per screen. No clutter. Big type. |
| Trust | Medical green palette. Neutral typography. No gimmicks. |
| Speed | The user is in pain. Every flow must be 3 clicks max. |
| Feedback | Every action gets a visible response within 200ms. |

---

## Color system

```
Primary      #1D9E75   (teal-green — medical, trustworthy)
Primary dark #0F6E56   (hover states, active elements)
Surface      #F8FAF9   (page background — near white with green tint)
Card         #FFFFFF   (elevated surfaces)
Text primary #1A1A1A   (near black — high contrast)
Text muted   #6B7280   (labels, captions)
Border       #E5E7EB   (subtle dividers)
Danger       #DC2626   (critical injury alerts)
Warning      #D97706   (moderate injury notices)
Success      #16A34A   (confirmation states)
```

### Usage rules

- Primary color appears only on the **one primary action** per screen (submit, book, confirm).
- Danger red is used **only** for the critical injury alert banner — never for decoration.
- Background is always `#F8FAF9`, never pure white — pure white feels sterile.
- Never use more than 3 colors on a single screen.

---

## Typography

```
Font family: Inter (Google Fonts — free)
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

Heading 1:  28px / weight 600 / line-height 1.3   (page titles)
Heading 2:  20px / weight 600 / line-height 1.4   (section titles)
Body:       16px / weight 400 / line-height 1.7   (all body text)
Label:      13px / weight 500 / line-height 1.5   (form labels, badges)
Caption:    12px / weight 400 / line-height 1.5   (helper text, timestamps)
```

**Rule:** Never go below 12px. Never use weight 700+ — it reads as panic.

---

## Component library (Tailwind CSS utility classes)

### Primary button
```html
<button class="bg-[#1D9E75] hover:bg-[#0F6E56] text-white font-medium
               px-6 py-3 rounded-lg transition-colors duration-200
               focus:outline-none focus:ring-2 focus:ring-[#1D9E75] focus:ring-offset-2">
  Submit Injury
</button>
```

### Form input
```html
<input class="w-full border border-gray-300 rounded-lg px-4 py-3
              text-gray-900 placeholder-gray-400 focus:outline-none
              focus:ring-2 focus:ring-[#1D9E75] focus:border-transparent
              transition-shadow duration-200" />
```

### Card container
```html
<div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
  <!-- content -->
</div>
```

### Critical injury alert
```html
<div class="bg-red-50 border border-red-200 rounded-lg p-4 flex gap-3">
  <span class="text-red-600 font-medium">Critical injury detected.</span>
  <p class="text-red-700 text-sm">A specialist appointment is recommended immediately.</p>
</div>
```

### Success confirmation
```html
<div class="bg-green-50 border border-green-200 rounded-lg p-4">
  <span class="text-green-700 font-medium">Appointment confirmed.</span>
</div>
```

---

## Page layout rules

```
Max content width:  680px (centered)
Page padding:       24px horizontal on mobile, 40px on desktop
Section spacing:    32px between major sections
Element spacing:    16px between form fields
```

Every page follows this structure:
```
[Nav bar — logo left, logout right]
[Page title — H1, left aligned]
[Single-purpose card or form]
[One primary action button — full width on mobile]
```

---

## Screen-by-screen UI guide

### Login / Signup
- Centered card, 480px max width
- Logo at top of card
- Username + password fields only on login
- No distracting links or ads
- Error message appears inline below the field that failed

### Injury form
- One column, mobile-first
- Body part selector: large tappable pill buttons (not a dropdown)
  — athletes may have shaky hands
- Text area for description: 4 rows minimum, clear placeholder
- Submit button: full width, Primary color, labeled "Assess my injury"

### Result page
- If critical: red alert banner at top, THEN assessment text, THEN "Book specialist" CTA
- If not critical: green banner, assessment text, "Book general appointment" CTA
- Never show both banners at once

### Appointment slots
- Grid of time-slot cards (2 columns on desktop, 1 on mobile)
- Each card shows: doctor name, specialty, date, time
- Selected card gets a Primary-color border ring
- One "Confirm booking" button below the grid

### Report view
- Print-optimised layout
- Hospital-style header: logo, patient name, date
- Sections: Diagnosis, Treatment Plan, Doctor Signature
- One "Download PDF" button (can be browser print for MVP)

---

## Alpine.js interaction patterns

### Body part selector (injury form)
```html
<div x-data="{ selected: '' }">
  <template x-for="part in ['Knee','Ankle','Shoulder','Thigh']">
    <button
      @click="selected = part"
      :class="selected === part
        ? 'bg-[#1D9E75] text-white border-[#1D9E75]'
        : 'bg-white text-gray-700 border-gray-300'"
      class="border-2 rounded-lg px-5 py-3 font-medium transition-colors"
      x-text="part">
    </button>
  </template>
  <input type="hidden" name="bodyPart" :value="selected">
</div>
```

### Slot selection (appointment screen)
```html
<div x-data="{ chosenSlot: null }">
  <!-- slot cards loop -->
  <div
    @click="chosenSlot = slot.id"
    :class="chosenSlot === slot.id ? 'ring-2 ring-[#1D9E75]' : ''"
    class="card cursor-pointer transition-all">
  </div>
  <button :disabled="!chosenSlot" type="submit"
    class="w-full primary-btn mt-4 disabled:opacity-40">
    Confirm booking
  </button>
</div>
```

---

## Accessibility rules (non-negotiable)

- All form inputs have a visible `<label>` — no placeholder-only labels
- Color is never the only indicator of state (always pair with an icon or text)
- Buttons have descriptive text — not just "Submit"
- Tab order follows visual order top-to-bottom
- Minimum touch target: 44px × 44px

---

## What to avoid

- No stock medical photos (needles, wounds, hospital imagery)
- No dark mode in MVP — adds complexity, save for v2
- No animations on page load — users want information immediately
- No modal dialogs for confirmations — use inline success states
- No more than one H1 per page