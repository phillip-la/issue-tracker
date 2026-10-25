import { useState } from 'react'
import './IssueForm.css'

function IssueForm({ onCreateIssue, editingIssue, onUpdateIssue }) {
    const [formData, setFormData] = useState({
        title: editingIssue?.title ?? '',
        description: editingIssue?.description ?? '',
        priority: editingIssue?.priority ?? 'MEDIUM',
        owner: editingIssue?.owner ?? '',
        startDate: editingIssue?.startDate ?? '',
        endDate: editingIssue?.endDate ?? ''
    })

    function handleChange(e) {
        const { name, value } = e.target

        setFormData(prev => ({
            ...prev,
            [name]: value
        }))
    }

    async function handleSubmit(e) {
        e.preventDefault()
        
        try {
            if (editingIssue) {
                await onUpdateIssue(editingIssue.id, formData)
            } else {
                await onCreateIssue(formData)
            }

            setFormData({
            title: '',
            description: '',
            priority: 'MEDIUM',
            owner: '',
            startDate: '',
            endDate: ''
            })
        } catch (error) {
            console.error('Unable to submit form:', error)
        }
    }
 
    return (
        <form className="issue-form" onSubmit={handleSubmit}>
            <input
                type="text"
                name="title"
                placeholder="Issue title"
                value={formData.title}
                onChange={handleChange}
                required
            />
            
            <textarea
                type="text"
                name="description"
                placeholder="Description"
                value={formData.description}
                onChange={handleChange}
            />

            <select 
                name="priority" 
                value={formData.priority}
                onChange={handleChange}
            >
                <option value="LOW">Low</option>
                <option value="MEDIUM">Medium</option>
                <option value="HIGH">High</option>
            </select>

            <input
                type="text"
                name="owner"
                placeholder="Issue owner"
                value={formData.owner}
                onChange={handleChange}
            />

            <input 
                type="date" 
                name="startDate"
                value={formData.startDate}
                onChange={handleChange}
            />

            <input
                type="date" 
                name="endDate" 
                value={formData.endDate}
                onChange={handleChange}
            />

            <button type="submit">
                {editingIssue ? 'Update issue' : 'Create issue'}
            </button>
        </form>
    )
}

export default IssueForm